<template>
  <el-container>
    <el-header>
      <el-row :gutter="10">
        <el-col :span="4">
          <el-input v-model="condition.blogTitle"
                    placeholder="输入文章标题">
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-select v-model="condition.blogCategoryId" placeholder="请选择文章分类">
            <el-option v-model="categoryEmpty"
                       label="----- 不选 -----">
            </el-option>
            <el-option
                v-for="item in categoryList"
                :key="item.categoryId"
                :label="item.categoryName"
                :value="item.categoryId">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-button type="primary"
                     icon="el-icon-search"
                     circle
                     :loading="loding"
                     @click="searchPage"
          >
          </el-button>

        </el-col>
        <el-col :span="4" :push="8">
          <el-button type="success"
                     icon="el-icon-refresh-right"
                     circle
                     @click="refreshPage"
          ></el-button>
        </el-col>
      </el-row>
    </el-header>
    <el-main>
      <el-table
          ref="multipleTable"
          :data="tableData"
          tooltip-effect="dark"
          style="width: 100%"
          v-loading="loading"
          :border="true"
          :header-cell-style="{background:'#e1e4e5',color:'#80878f','text-align':'center'}"
          :cell-style="{'text-align':'center'}"
          @selection-change="handleSelectionChange">
        <el-table-column
            type="selection"
            width="59">
        </el-table-column>
        <el-table-column
            prop="blogTitle"
            label="博客标题"
            width="200">
          <!--          <template slot-scope="scope">{{ scope.row.date }}</template>-->
        </el-table-column>
        <el-table-column
            prop="blogCategoryName"
            label="博客分类"
            >
        </el-table-column>
        <el-table-column
            prop="blogTags"
            label="博客标签"

        >
        </el-table-column>
        <el-table-column
            prop="blogViews"
            label="阅读量"
            >
        </el-table-column>
        <el-table-column
            prop="updateTime"
            label="最后修改时间"
            >
          <template slot-scope="scope">{{ scope.row.updateTime | moment }}</template>
        </el-table-column>
        <el-table-column
            label="文章状态"

            >
          <template slot-scope="scope">
            <el-switch
                class="switch"
                v-model="scope.row.blogStatus === 1 ? true :false"
                active-text="发布"
                inactive-text="草稿"
                active-color="#13ce66"
                @click.native="editBlogStatus(scope.row.blogId,scope.row.blogStatus)"
                v-loading.fullscreen.lock="fullscreenLoading"
                inactive-color="#ff4949">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column
            prop="name"
            label="评论"
            >
          <template slot-scope="scope">
            <el-switch
                class="switch"
                v-model="scope.row.enableComment == 1 ? true :false"
                active-text="允许评论"
                inactive-text="禁止评论"
                active-color="#13ce66"
                inactive-color="#ff4949"
                @click.native="editEnableComment(scope.row.blogId,scope.row.enableComment)"
            >
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column
            prop="name"
            label="操作"
            width="250"
            show-overflow-tooltip

            >
          <template slot-scope="scope">

            <el-button
                @click="editBlog(scope.row.blogId)"
                circle>
              编辑
            </el-button>
            <el-button type="danger"
                       circle
                       v-if="scope.row.isDeleted == 0"
                       @click="deleteBlog(scope.row.blogId)">
              删除
            </el-button>
            <el-button type="warning"
                       circle
                       v-if="scope.row.isDeleted == 1"
                       @click="restoreBlog(scope.row.blogId)">
              还原
            </el-button>
            <el-button type="danger"
                       circle
                       @click="clearBlog(scope.row.blogId)">
              清除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="condition.pageNum"
          :page-sizes="[5, 15, 25, 35]"
          :page-size="condition.pageSize"
          layout="total,sizes, prev, pager, next, jumper"
          :total="totalSize">
      </el-pagination>
    </el-main>
  </el-container>
</template>

<script>
import {clearBlog, deleteBlog, pageBlog, restoreBlog, updateBlogStatus} from "../../../../api/blogmanager/blog";
import {getBlogCategoryList} from "../../../../api/blogmanager/blogCategory";
import qs from "qs";

export default {
  name: "BlogList",
  data() {
    return {
      fullscreenLoading: false,
      loading: true,
      tableData: [],
      condition: {
        pageNum: 0,
        pageSize: 5,
        blogTitle: null,
        blogCategoryId: null
      },
      totalSize: 10,
      multipleSelection: [],
      value: '',
      loding: false,
      blogCategoryId: '',
      categoryList: [],
      categoryEmpty: null,
    }
  },
  created() {
    this.handleCurrentChange(1);
    this.getBlogCategory();
  },
  methods: {
    restoreBlog(blogId) {
      const _this = this;
      this.$confirm('此操作将还原该文章, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        _this.fullscreenLoading = true;
        restoreBlog(qs.stringify({
          blogId: blogId
        })).then(res => {
          if (res.code == 2000) {
            _this.handleCurrentChange(1);
            setTimeout(() => {
              this.fullscreenLoading = false;
              this.$message({
                type: 'success',
                message: '还原成功!'
              });
            }, 500);
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消还原'
        });
      });
    },
    editEnableComment(id, enableComment) {
      enableComment = enableComment == 0 ? 1 : 0;
      const _this = this;
      this.fullscreenLoading = true;
      updateBlogStatus(qs.stringify({
        blogId: id,
        enableComment: enableComment
      })).then(res => {
        if (res.code == 2000) {
          _this.handleCurrentChange(1);
          setTimeout(() => {
            this.fullscreenLoading = false;
            this.$message({
              message: '修改成功',
              center: true,
              type: 'success'
            });
          }, 500);
        }
      })
    },
    editBlogStatus(id, blogStatus) {
      this.fullscreenLoading = true;
      blogStatus = blogStatus == 0 ? 1 : 0;
      const _this = this;
      updateBlogStatus(qs.stringify({
        blogId: id, blogStatus: blogStatus
      })).then(res => {
        if (res.code == 2000) {
          _this.handleCurrentChange(1);
          setTimeout(() => {
            this.fullscreenLoading = false;
            this.$message({
              message: '修改成功',
              center: true,
              type: 'success'
            });
          }, 500);
        }
      })
    },
    editBlog(blogId) {
      this.$router.push(
          {
            path: '/editBlog',
            query: {
              blogId: blogId
            }
          })
    },
    clearBlog(blogId) {
      const _this = this;
      this.$confirm('此操作将请清除数据库, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        _this.fullscreenLoading = true;
        clearBlog(qs.stringify({
          blogId: blogId
        })).then(res => {
          if (res.code == 2000) {
            _this.handleCurrentChange(1);
            setTimeout(() => {
              this.fullscreenLoading = false;
              this.$message({
                type: 'success',
                message: '清除数据库成功!'
              });
            }, 500);
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消清除数据库'
        });
      });
    },
    deleteBlog(blogId) {
      const _this = this;
      this.$confirm('此操作将永久删除该文章, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        _this.fullscreenLoading = true;
        deleteBlog(qs.stringify({
          blogId: blogId
        })).then(res => {
          if (res.code == 2000) {
            _this.handleCurrentChange(1);
            setTimeout(() => {
              this.fullscreenLoading = false;
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
            }, 500);
          }
        })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    refreshPage() {
      this.loading = true;
      this.condition.blogTitle = null;
      this.condition.blogCategoryId = null;
      this.handleCurrentChange(1);
    },
    searchPage() {
      this.loding = true;
      this.handleCurrentChange(1);
      this.loding = false;
    },
    getBlogCategory() {
      getBlogCategoryList().then(res => {
        this.categoryList = res;
      })
    },
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleSizeChange(val) {
      const _this = this;
      this.condition.pageSize = val;
      pageBlog(qs.stringify(this.condition)).then(res => {
        _this.loading = false;
        this.totalSize = res.totalSize;
        this.tableData = res.data;
      })
    },
    handleCurrentChange(val) {
      const _this = this;
      this.condition.pageNum = val;
      pageBlog(qs.stringify(this.condition)).then(res => {

        this.totalSize = res.totalSize;
        this.tableData = res.data;
        _this.loading = false;
      })
    }
  }
}
</script>

<style>

</style>
