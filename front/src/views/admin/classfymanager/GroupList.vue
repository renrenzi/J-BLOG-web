<template>
  <el-container>
    <el-header>
      <el-row :gutter="10">
        <el-col :span="4">
          <el-input :gutter="10" v-model="condition.categoryName" placeholder="请输入分类名"></el-input>
        </el-col>

        <el-col :span="2">
          <el-button type="primary"
                     icon="el-icon-search"
                     @click="searchCategory"
                     circle
          >
          </el-button>

        </el-col>
        <el-col :span="8" :push="10">
          <el-button type="primary"
                     @click="categoryDialog = true">
            添加分类信息
          </el-button>
          <el-button type="success"
                     icon="el-icon-refresh-right"
                     circle
                     @click="searchCategory"
          ></el-button>
        </el-col>
        <el-dialog
            title="添加分类信息"
            :visible.sync="categoryDialog"
            width="30%"
            center>
          <el-row>
            分类名
            <el-input v-model="categoryName"></el-input>
          </el-row>
          <el-row>
            排序值
            <el-input v-model="categoryRank"></el-input>
          </el-row>
          <span slot="footer" class="dialog-footer">
            <el-button @click="categoryDialog = false">取 消</el-button>
            <el-button type="primary" @click="addCategory">确 定</el-button>
          </span>
        </el-dialog>
        <el-dialog
            title="修改分类信息"
            :visible.sync="editCategoryDialog"
            width="30%"
            center>
          <el-row>
            分类名
            <el-input v-model="categoryName"></el-input>
          </el-row>
          <el-row>
            排序值
            <el-input v-model="categoryRank"></el-input>
          </el-row>
          <span slot="footer" class="dialog-footer">
            <el-button @click="categoryDialog = false">取 消</el-button>
            <el-button type="primary" @click="updateCategory">确 定</el-button>
          </span>
        </el-dialog>
      </el-row>
    </el-header>
    <el-main>
      <el-table
          id="boxbox"
          ref="multipleTable"
          :data="tableData"
          tooltip-effect="dark"
          style="width: 100%"
          v-loading="fullscreenLoading"
          :border="true"
          :header-cell-style="{background:'#e1e4e5',color:'#80878f','text-align':'center'}"
          :cell-style="{'text-align':'center'}"
          @selection-change="handleSelectionChange">
        <el-table-column
            type="selection"
            width="57">
        </el-table-column>
        <el-table-column
            prop="categoryName"
            label="分类名"
            width="268">
        </el-table-column>
        <el-table-column
            prop="categoryRank"
            label="排序值"
            width="268">
        </el-table-column>
        <el-table-column
            prop="address"
            label="当前状态"
            width="268"
        >
          <template slot-scope="scope">
            <el-switch
                class="switch"
                v-model="scope.row.isDeleted == 1 ? true :false"
                active-text="已删除"
                inactive-text="未删除"
                active-color="#13ce66"
                @click.native="editDelete(scope.row.categoryId,scope.row.isDeleted)"
                inactive-color="#ff4949">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column
            prop="name"
            label="操作"
            show-overflow-tooltip
            width="268">
          <template slot-scope="scope">
            <el-button type="primary"
                       circle
                       icon="el-icon-edit"
                       @click="editCategory(scope.row.categoryId)"
            >
            </el-button>
            <el-button type="danger"
                       @click="clearCategory(scope.row.categoryId)"
                       icon="el-icon-delete" circle>
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="condition.pageNum"
          :page-size="condition.pageSize"
          :page-sizes="[5, 15, 25, 35]"
          layout="total,sizes,prev, pager, next, jumper"
          :total="totalSize">
      </el-pagination>
    </el-main>
  </el-container>
</template>

<script>
import qs from "qs";
import {
  addCategory,
  clearCategory,
  getCategory,
  pageCategory,
  updateCategoryStatus
} from "../../../api/blogmanager/blogCategory";
import {clearTag} from "../../../api/blogmanager/blogTag";

export default {
  name: "GroupList",
  data() {
    return {
      categoryId: '',
      categoryName: '',
      categoryRank: '',
      editCategoryDialog: false,
      categoryDialog: false,
      fullscreenLoading: false,
      tableData: [],
      multipleSelection: [],
      condition: {
        pageNum: 1,
        pageSize: 5,
        categoryName: ''
      },
      totalSize: 10,
      currentPage: 1,
    }
  },
  created() {
    this.handleCurrentChange(1);
  },
  methods: {
    updateCategory() {
      const _this = this;
      updateCategoryStatus(qs.stringify({
        categoryId: this.categoryId,
        categoryName: this.categoryName,
        categoryRank: this.categoryRank,
      })).then(res => {
        if (res.code === 2000) {
          _this.editCategoryDialog = false;
          _this.handleCurrentChange(1);
          this.$message({
            type: 'success',
            message: '修改分类成功!'
          });
        }
      })
    },
    editCategory(id) {
      const _this = this;
      this.categoryId = id;
      getCategory(qs.stringify({
        categoryId: id
      })).then(res => {
        if (res.code === 2000) {
          _this.categoryName = res.data.categoryName;
          _this.categoryRank = res.data.categoryRank;
          _this.editCategoryDialog = true;
        }
      })
    },
    clearCategory(id) {
      const _this = this;
      this.$confirm(' 删除分类时,会将跟它绑定的文章全部重置为默认分类! 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        clearCategory(qs.stringify({
          categoryId: id
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
    addCategory() {
      const _this = this;
      addCategory(qs.stringify({
        categoryName: this.categoryName,
        categoryRank: this.categoryRank
      })).then(res => {
        if (res.code == 2000) {
          _this.categoryName = '';
          _this.categoryRank = '';
          _this.categoryDialog = false;
          _this.handleCurrentChange(1);
          this.$message({
            type: 'success',
            message: '添加成功!'
          });
        }
      })
    },
    editDelete(id, isDeleted) {
      isDeleted = isDeleted == 1 ? 0 : 1;
      const _this = this;
      updateCategoryStatus(qs.stringify({
        categoryId: id,
        isDeleted: isDeleted
      })).then(res => {
        if (res.code == 2000) {
          _this.handleCurrentChange(1);
        }
      })
    },
    searchCategory() {
      this.handleCurrentChange(1);
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
      const _this = this;
      this.fullscreenLoading = true;
      pageCategory(qs.stringify(this.condition)).then(res => {
        _this.totalSize = res.totalSize;
        _this.tableData = res.data;
        setTimeout(() => {
          this.fullscreenLoading = false;
        }, 500);
      })
    },
    handleCurrentChange(val) {
      this.condition.pageNum = val;
      const _this = this;
      this.fullscreenLoading = true;
      pageCategory(qs.stringify(this.condition)).then(res => {
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
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}

/* switch按钮样式 */
.switch .el-switch__label {
  position: absolute;
  display: none;
  color: #fff !important;
}

/*打开时文字位置设置*/
.switch .el-switch__label--right {
  z-index: 1;
}

/* 调整打开时文字的显示位子 */
.switch .el-switch__label--right span {
  margin-right: 9px;
}

/*关闭时文字位置设置*/
.switch .el-switch__label--left {
  z-index: 1;
}

/* 调整关闭时文字的显示位子 */
.switch .el-switch__label--left span {
  margin-left: 9px;
}

/*显示文字*/
.switch .el-switch__label.is-active {
  display: block;
}

/* 调整按钮的宽度 */
.switch.el-switch .el-switch__core,
.el-switch .el-switch__label {
  width: 70px !important;
  margin: 0;
}
</style>
