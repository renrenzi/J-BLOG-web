<template>
  <el-container>
    <el-header>
      <el-dialog
          title="分配角色"
          :visible.sync="isDivide"
          width="30%"
          center>
        <el-row>
          <el-col :span="16">
            <el-select v-model="userRoleList" multiple placeholder="请选择角色标签">
              <el-option
                  v-for="item in roleList"
                  :key="item.roleId"
                  :label="item.roleName"
                  @click.native="handleRoleId(item.roleId)"
                  :value="item.roleName">
              </el-option>
            </el-select>
          </el-col>
        </el-row>
        <span slot="footer" class="dialog-footer">
            <el-button @click="isDivide = false">取 消</el-button>
            <el-button type="primary" @click="toAllocateRole()">确 定</el-button>
          </span>
      </el-dialog>

      <el-row>
        <el-card id="headCard">
          <el-row>
            <el-col :span="2">
              <icon class="el-icon-share">
                <span>筛选</span>
              </icon>
            </el-col>
            <el-col :span="4" push="20">
              <el-button type="primary"
                         icon="el-icon-search"
                         @click="searchUser()"
                         size="small">
              </el-button>
              <el-button type="success"
                         icon="el-icon-refresh-right"
                         size="small"
                         @click="reload()">

              </el-button>
            </el-col>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="4">
              <el-input :gutter="10" v-model="condition.loginUserName" placeholder="请输入用户名"></el-input>
            </el-col>
            <el-col :span="2" :push="18">
              <el-button
                  @click="isAddOrEdit = true, isEdit = false, adminUser={}">
                添加
              </el-button>
            </el-col>
            <el-dialog
                title="添加用户"
                :visible.sync="isAddOrEdit"
                width="30%"
                center>
              <el-row>
                <el-col span="6">
                  用户名称：
                </el-col>
                <el-col span="16">
                  <el-input
                      placeholder="请输入用户名"
                      v-model="adminUser.loginUserName">

                  </el-input>
                </el-col>
              </el-row>
              <el-row>
                <el-col span="6">
                  用户密码：
                </el-col>
                <el-col span="16">
                  <el-input
                      type="password"
                      :rows="4"
                      placeholder="请输入密码"
                      v-model="adminUser.loginPassword">
                  </el-input>
                </el-col>
              </el-row>
              <el-row>
                <el-col span="6">
                  用户昵称：
                </el-col>
                <el-col span="16">
                  <el-input
                      placeholder="请输入昵称"
                      v-model="adminUser.nickName">
                  </el-input>
                </el-col>
              </el-row>
              <el-row>
                <el-col span="6">
                  是否启用：
                </el-col>
                <el-col span="4">
                  <el-switch
                      class="switch"
                      v-model="adminUser.locked === 1"
                      active-text="启用"
                      inactive-text="禁用"
                      active-color="#13ce66"
                      @click.native="adminUser.locked = adminUser.locked === 1 ? 0 : 1"
                      inactive-color="#ff4949">
                  </el-switch>
                </el-col>
              </el-row>

              <span slot="footer" class="dialog-footer">
            <el-button @click="isAddOrEdit = false">取 消</el-button>
            <el-button type="primary" @click="addUser()">确 定</el-button>
          </span>
            </el-dialog>
          </el-row>
        </el-card>
      </el-row>
      <el-row>
        <el-table
            id="boxbox"
            ref="multipleTable"
            :data="tableData"
            tooltip-effect="dark"
            style="width: 95%;margin-left: 10px;margin-top: 10px"
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
              type="index"
              label="编号"
              width="50">
          </el-table-column>
          <el-table-column
              prop="loginUserName"
              label="用户名"
              >
          </el-table-column>
          <el-table-column
              prop="nickName"
              label="昵称"
             >
          </el-table-column>
          <el-table-column
              prop="imgUrl"
              label="头像"

          >
            <template slot-scope="scope">
              <el-image
                  style="width: 30px; height: 30px"
                  :src="scope.row.imgUrl"
                  fit="fill">
              </el-image>
            </template>
          </el-table-column>
          <el-table-column
              prop="address"
              label="当前状态"
              width="221"
          >
            <template slot-scope="scope">
              <el-switch
                  class="switch"
                  v-model="scope.row.locked === 1 ? true : false"
                  active-text="已锁定"
                  inactive-text="未锁定"
                  active-color="#13ce66"
                  @click.native="handleLockedChange(scope.row.adminUserId, scope.row.locked)"
                  inactive-color="#ff4949">
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column
              prop="name"
              label="操作"
              show-overflow-tooltip
              width="245">
            <template slot-scope="scope">
              <el-button size="small"
                          @click="handleAllocateUser(scope.row.adminUserId)">
                分配角色
              </el-button>
              <el-button type="primary"
                         size="small"
                         icon="el-icon-edit"
                         @click="handleEditUser(scope.row)">
              </el-button>
              <el-button type="danger"
                         @click="deleteUser(scope.row.adminUserId)"
                         icon="el-icon-delete"
                         size="small">
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
            @size-change="handleSizeChange"
            style="margin-left: 10px"
            @current-change="handleCurrentChange"
            :current-page="condition.pageNum"
            :page-size="condition.pageSize"
            :page-sizes="[5, 15, 25, 35]"
            layout="total,sizes,prev, pager, next, jumper"
            :total="totalSize">
        </el-pagination>
      </el-row>
    </el-header>
  </el-container>
</template>

<script>
import qs from "qs";
import {deleteUsers, editUserInfo, pageUser, registerUser} from "../../../../api/blogmanager/admin";
import {pageRole} from "../../../../api/blogmanager/userRole";
import {allocateRole, getRoleListById} from "../../../../api/blogmanager/userRoleRelation";

export default {
  name: "UserManager",
  data() {
    return {
      adminUserId: null,
      requestIds: null,
      roleList: {},
      userRoleList:[],
      adminUser: {
        locked: 0,
      },
      isDivide: false,
      loginUserName: null,
      isEdit: false,
      isAddOrEdit: false,
      fullscreenLoading: false,
      tableData: [],
      multipleSelection: [],
      condition: {
        pageNum: 1,
        pageSize: 5,
        loginUserName: null
      },
      totalSize: 10,
    }
  },
  created() {
    this.handleCurrentChange(1);
    this.getRoleList();
  },
  methods: {
    reload(){
      this.condition.loginUserName = null;
      this.handleCurrentChange(1);
    },
    searchUser(){
      this.handleCurrentChange(1);
    },
    toAllocateRole(){

      let params = new URLSearchParams();
      params.append("adminId", this.adminUserId);
      params.append("roleIds", Array.from(this.requestIds));
      allocateRole(params).then(res => {
        if(res.code === 2000){
          this.isDivide = false;
        }
      })
    },
    handleAllocateUser(adminUserId){
      this.isDivide=true;
      this.adminUserId = adminUserId;
      getRoleListById(qs.stringify({
        adminId: adminUserId
      })).then(res => {
         const list =  res.data
        this.requestIds = new Set();
        this.userRoleList = [];
        for(let i = 0; i < list.length; i++){
          this.userRoleList.push(list[i].roleName);
          this.requestIds.add(list[i].roleId);
        }
      })
    },
    getRoleList(){
      pageRole(qs.stringify({
        pageNum:1,
        pageSize:99
      })).then(res => {
           this.roleList = res.data;
      })
    },
    handleRoleId(roleId){
      if(this.requestIds.has(roleId)){
        this.requestIds.delete(roleId);
      }else{
        this.requestIds.add(roleId);
      }
    },
    handleLockedChange(adminUserId, locked){
      editUserInfo(qs.stringify({
        adminUserId: adminUserId,
        locked: locked ? 0 : 1
      }))
      this.tableData = [];
      this.handleCurrentChange(1);
    },
    handleEditUser(adminUser){
      this.adminUser = adminUser;
      this.isEdit = true;
      this.isAddOrEdit = true;
    },
    deleteUser(adminUserId) {
      this.$confirm('此操作将永久删除该资源, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        deleteUsers(qs.stringify({ids: adminUserId})).then(res => {
          if (res.code === 2000) {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.handleCurrentChange(1);
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },

    addUser() {
      if (!this.isEdit) {
        registerUser(qs.stringify(this.adminUser))
      }else{
        editUserInfo(qs.stringify(this.adminUser))
      }
      this.handleCurrentChange(1);
      this.isAddOrEdit = false;
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
      pageUser(qs.stringify(this.condition)).then(res => {
        this.totalSize = res.totalSize;
        this.tableData = res.data;
        setTimeout(() => {
          this.fullscreenLoading = false;
        }, 500);
      })
    },
    handleCurrentChange(val) {
      this.condition.pageNum = val;
      this.fullscreenLoading = true;
      pageUser(qs.stringify(this.condition)).then(res => {
        this.totalSize = res.totalSize;
        this.tableData = res.data;
      })
      setTimeout(() => {
        this.fullscreenLoading = false;
      }, 500);
    }
  }
}
</script>

<style scoped>
#headCard {
  margin-left: 10px;
  width: 95%;
}

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
  margin-right: 5px;
}

/*关闭时文字位置设置*/
.switch .el-switch__label--left {
  z-index: 1;
}

/* 调整关闭时文字的显示位子 */
.switch .el-switch__label--left span {
  margin-left: 5px;
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
