<template>
  <el-container>
    <el-header>
      <el-row :gutter="10">
        <el-col :span="4">
          <el-select v-model="condition.blogId" placeholder="请选择博客标题">
            <el-option
                v-for="item in blogList"
                :key="item.blogId"
                :label="item.blogTitle"
                :value="item.blogId">
            </el-option>
          </el-select>
        </el-col>

        <el-col :span="4">
          <el-button type="primary"
                     icon="el-icon-search"
                     circle
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
          :border="true"
          v-loading="fullscreenLoading"
          :header-cell-style="{background:'#e1e4e5',color:'#80878f','text-align':'center'}"
          :cell-style="{'text-align':'center'}"
          @selection-change="handleSelectionChange">
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            prop="commentator"
            label="评论者名称"
            width="200">
        </el-table-column>
        <el-table-column
            prop="email"
            label="评论者邮箱"
            width="168">
        </el-table-column>
        <el-table-column
            prop="commentBody"
            label="评论内容"
            width="100">
        </el-table-column>
        <el-table-column
            prop="commentCreateTime"
            label="评论提交时间"
            >
          <template slot-scope="scope">{{ scope.row.commentCreateTime | moment }}</template>
        </el-table-column>
        <el-table-column
            prop="replyBody"
            label="回复内容"
            >
        </el-table-column>
        <el-table-column
            prop="replyCreateTime"
            label="回复时间"
           >
          <template slot-scope="scope">{{ scope.row.replyCreateTime | moment }}</template>
        </el-table-column>
        <el-table-column
            prop="name"
            label="审核"
            >
          <template slot-scope="scope">
            <el-switch
                class="switch"
                v-model="scope.row.commentStatus === 1 ? true :false"
                active-text="已审核"
                inactive-text="未审核"
                active-color="#13ce66"
                @click.native="editCommentStatus(scope.row.commentId,scope.row.commentStatus)"
                inactive-color="#ff4949">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column
            prop="name"
            label="当前状态"
            >
          <template slot-scope="scope">
            <el-switch
                class="switch"
                v-model="scope.row.isDeleted === 1 ? true :false"
                active-text="已删除"
                inactive-text="未删除"
                active-color="#13ce66"
                @click.native="editDelete(scope.row.commentId,scope.row.isDeleted)"
                inactive-color="#ff4949">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column
            prop="name"
            label="操作"
            show-overflow-tooltip
            >
          <template slot-scope="scope">
            <el-button type="primary"
                       circle
                       icon="el-icon-edit"
                       @click="editComment(scope.row.commentId)"
            >
            </el-button>
            <el-button type="danger"
                       icon="el-icon-delete" circle
                       @click="clearComment(scope.row.commentId)"
            >
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="condition.pageSize"
          :page-sizes="[5, 15, 25, 35]"
          layout="total,sizes, prev, pager, next, jumper"
          :total="totalSize">
      </el-pagination>
    </el-main>

    <el-dialog title="修改评论信息"
               :visible.sync="commentDialog"
               width="35%"
               center>
      <el-row type="flex" justify="center">
        <el-col :span="4">
          回复内容:
        </el-col>
        <el-col :span="20">
          <el-input v-model="commentInfo.replyBody"></el-input>
        </el-col>
      </el-row>

      <span slot="footer" class="dialog-footer">
            <el-button @click="commentDialog = false">取 消</el-button>
            <el-button type="primary" @click="updateComment">确 定</el-button>
          </span>
    </el-dialog>
  </el-container>
</template>

<script>
import {getBlogList} from "../../../../api/blogmanager/blog";
import {deleteComment, getComment, pageComment, updateCommentStatus} from "../../../../api/blogmanager/blogComment";
import qs from "qs";

export default {
  name: "CommentList",
  data() {
    return {
      commentInfo: {
        commentId: '',
        replyBody: ''
      },
      commentDialog: false,
      fullscreenLoading: false,
      blogList: [],
      tableData: [],
      condition: {
        pageNum: 1,
        pageSize: 5,
        blogId: ''
      },
      totalSize: 10,
      multipleSelection: [],
      currentPage: 1,
    }
  },
  created() {
    this.getBlogList();
    this.handleCurrentChange(1);
  },
  methods: {
    updateComment(){
      const _this = this;
      updateCommentStatus(qs.stringify({
        commentId: this.commentInfo.commentId,
        replyBody: this.commentInfo.replyBody
      })).then(res => {
        if (res.code === 2000){
          _this.handleCurrentChange(1);
          _this.commentDialog = false;
        }
      })
    },
    editComment(id) {
      const _this = this;
      getComment(qs.stringify({
        commentId: id
      })).then(res => {
        if (res.code === 2000){
          _this.commentInfo = res.data;
          _this.commentDialog = true;
        }
      })
    },
    clearComment(id) {
      const _this = this;
      this.$confirm('此操作将请清除数据库, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        _this.fullscreenLoading = true;
        deleteComment(qs.stringify({
          commentId: id
        })).then(res => {
          _this.handleCurrentChange(1);
          this.$message({
            type: 'success',
            message: '清除数据库成功!'
          });
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消清除数据库'
        });
      });
    },
    editDelete(id, isDeleted) {
      this.fullscreenLoading = true
      isDeleted = isDeleted == 0 ? 1 : 0;
      const _this = this;
      updateCommentStatus(qs.stringify({
        commentId: id, isDeleted: isDeleted
      })).then(res => {
        _this.handleCurrentChange(1);
      })
    },
    editCommentStatus(id, commentStatus) {
      this.fullscreenLoading = true
      commentStatus = commentStatus == 0 ? 1 : 0;
      const _this = this;
      updateCommentStatus(qs.stringify({
        commentId: id, commentStatus: commentStatus
      })).then(res => {
        _this.handleCurrentChange(1);
      })
    },
    refreshPage() {
      this.tableData = [];
      this.handleCurrentChange(1);
    },
    searchPage() {
      this.tableData = [];
      this.loding = true;
      this.handleCurrentChange(1);
      this.loding = false;
    },
    getBlogList() {
      getBlogList().then(res => {
        this.blogList = res;
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
      this.condition.pageSize = val;
      this.fullscreenLoading = true;
      const _this = this;
      pageComment(qs.stringify(this.condition)).then(res => {
        _this.totalSize = res.totalSize;
        _this.tableData = res.data;
        setTimeout(() => {
          this.fullscreenLoading = false;
        }, 500);
      })
    },
    handleCurrentChange(val) {
      this.condition.pageNum = val;
      this.fullscreenLoading = true;
      const _this = this;
      pageComment(qs.stringify(this.condition)).then(res => {
        _this.totalSize = res.totalSize;
        _this.tableData = res.data;
        setTimeout(() => {
          this.fullscreenLoading = false;
        }, 500);
      })
    }
  }
}
</script>


<style scoped>

</style>
