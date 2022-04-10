<template>
  <el-container style="background: rgb(238, 241, 246)">
    <el-header style="background: rgb(255,255,255)" height="80px">
      <el-row style="background: rgb(238, 241, 246);height: 40px;margin-top: 20px;margin-left: 10px">
        欢迎管理员：
        <span style="color: red">{{this.$store.state.adminUser.nickName}}</span>
        <el-divider direction="vertical"></el-divider>
        当前时间:{{ date | moment }}
      </el-row>
    </el-header>
    <br>
    <div class="address-layout">
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="out-border">
            <div class="layout-title">文章数</div>
            <div class="color-main address-content">
              {{count.info}}
<!--              <a href="https://github.com/macrozheng/mall">mall</a>-->
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="out-border">
            <div class="layout-title">分类数</div>
            <div class="color-main address-content">
              {{count.category}}
<!--              <a href="https://github.com/macrozheng/mall-admin-web">mall-admin-web</a>-->
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="out-border">
            <div class="layout-title">标签数</div>
            <div class="color-main address-content">
              {{count.tag}}
<!--              <a href="https://github.com/macrozheng/mall-learning">mall-learning</a>-->
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
       <div class="address-layout">
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="out-border">
            <div class="layout-title">评论数</div>
            <div class="color-main address-content">
              {{count.comment}}
<!--              <a href="https://github.com/macrozheng/mall">mall</a>-->
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="out-border">
            <div class="layout-title">友情链接</div>
            <div class="color-main address-content">
              {{count.link}}
<!--              <a href="https://github.com/macrozheng/mall-admin-web">mall-admin-web</a>-->
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="out-border">
            <div class="layout-title">热门文章</div>
            <div class="color-main address-content">
              999
<!--              <a href="https://github.com/macrozheng/mall-learning">mall-learning</a>-->
            </div>
          </div>
        </el-col>
      </el-row>
    </div>


    <!-- <el-main style="background: rgb(255,255,255);height: 170px">
      <el-row>
        <span>数据统计</span>
        <el-divider></el-divider>
      </el-row>
      <el-row :gutter="10" style="margin-inline-end: 15px">
        <el-col :span="4" :offset="1">
            <el-card shadow="hover" style="background:  rgb(238, 241, 246)">
              <el-row>
                文章数
              </el-row>
              <el-row>
                <span style="font-size: 20px;color: greenyellow">{{count.info}}</span>
              </el-row>
            </el-card>
        </el-col>
        <el-col :span="4">
          <el-card shadow="hover" style="background:  rgb(238, 241, 246)">
            <el-row>
              分类数
            </el-row>
            <el-row>
              <span style="font-size: 20px;color: greenyellow">{{count.category}}</span>
            </el-row>
          </el-card>
        </el-col>
        <el-col :span="4">
          <el-card shadow="hover" style="background:  rgb(238, 241, 246)">
            <el-row>
              标签数
            </el-row>
            <el-row>
              <span style="font-size: 20px;color: greenyellow">{{count.tag}}</span>
            </el-row>
          </el-card>
        </el-col>
        <el-col :span="4">
          <el-card shadow="hover" style="background:  rgb(238, 241, 246)">
            <el-row>
              评论数
            </el-row>
            <el-row>
              <span style="font-size: 20px;color: greenyellow">{{count.comment}}</span>
            </el-row>
          </el-card>
        </el-col>
        <el-col :span="4">
          <el-card shadow="hover" style="background:  rgb(238, 241, 246)">
            <el-row>
              友情链接
            </el-row>
            <el-row>
              <span style="font-size: 20px;color: greenyellow">{{count.link}}</span>
            </el-row>
          </el-card>
        </el-col>
      </el-row>
    </el-main> -->
    <br>
    <el-footer style="background: rgb(255,255,255);height: 500px">
      <el-row>
        <span>系统信息&开发团队</span>
        <el-divider></el-divider>
      </el-row>
      <el-row style="margin-left: 10px">
        <el-table
            :header-cell-style="{background:'#e1e4e5',color:'#80878f'}"
            ref="multipleTable"
            :data="tableData"
            tooltip-effect="dark"
            style="width: 100%"
            :border="true">
          <el-table-column
              prop="configName"
              label="参数名"
              width="322">
          </el-table-column>
          <el-table-column
              prop="configValue"
              label="参数值"
              width="790">
          </el-table-column>
        </el-table>
      </el-row>

    </el-footer>
  </el-container>
</template>

<script>
import {getConfigList} from "../../api/blogmanager/blogConfig";
import {getCount} from "../../api/blogmanager/admin";

export default {
  name: "Home",
  data() {
    return {
      tableData: [],
      date: new Date(),
      count: {
        info: '',
        category: '',
        comment: '',
        tag: '',
        link: '',
      }
    };
  },
  created() {
    this.getConfigList();
    this.getCounts();
  },
  methods: {
    getCounts(){
      const _this = this;
      getCount().then(res => {
        if (res.code === 2000){
          _this.count = res.data;
        }
      })
    },
    getConfigList() {
      getConfigList().then(res => {
        if (res.code === 2000) {
          this.tableData = res.data;
        }
      })
    }
  },
  mounted() {
    let _this = this; // 声明一个变量指向Vue实例this，保证作用域一致
    this.timer = setInterval(() => {
      _this.date = new Date(); // 修改数据date
    }, 1000)
  },
  beforeDestroy() {
    if (this.timer) {
      clearInterval(this.timer); // 在Vue实例销毁前，清除我们的定时器
    }
  }
}
</script>

<style scoped>
 .app-container {
    margin-top: 40px;
    margin-left: 120px;
    margin-right: 120px;
  }

  .address-layout {
  }

  .total-layout {
    margin-top: 20px;
  }

  .total-frame {
    border: 1px solid #DCDFE6;
    padding: 20px;
    height: 100px;
  }

  .total-icon {
    color: #409EFF;
    width: 60px;
    height: 60px;
  }

  .total-title {
    position: relative;
    font-size: 16px;
    color: #909399;
    left: 70px;
    top: -50px;
  }

  .total-value {
    position: relative;
    font-size: 18px;
    color: #606266;
    left: 70px;
    top: -40px;
  }

  .un-handle-layout {
    margin-top: 20px;
    border: 1px solid #DCDFE6;
  }

  .layout-title {
    color: #606266;
    padding: 15px 20px;
    background: #F2F6FC;
    font-weight: bold;
  }

  .un-handle-content {
    padding: 20px 40px;
  }

  .un-handle-item {
    border-bottom: 1px solid #EBEEF5;
    padding: 10px;
  }

  .overview-layout {
    margin-top: 20px;
  }

  .overview-item-value {
    font-size: 24px;
    text-align: center;
  }

  .overview-item-title {
    margin-top: 10px;
    text-align: center;
  }

  .out-border {
    border: 1px solid #DCDFE6;
  }

  .statistics-layout {
    margin-top: 20px;
    border: 1px solid #DCDFE6;
  }
  .mine-layout {
    position: absolute;
    right: 140px;
    top: 107px;
    width: 250px;
    height: 235px;
  }
  .address-content{
    padding: 20px;
    font-size: 18px
  }.app-container {
    margin-top: 40px;
    margin-left: 120px;
    margin-right: 120px;
  }

  .address-layout {
    background: rgb(255,255,255);
  }


  .total-layout {
    margin-top: 20px;
  }

  .total-frame {
    border: 1px solid #DCDFE6;
    padding: 20px;
    height: 100px;
  }

  .total-icon {
    color: #409EFF;
    width: 60px;
    height: 60px;
  }

  .total-title {
    position: relative;
    font-size: 16px;
    color: #909399;
    left: 70px;
    top: -50px;
  }

  .total-value {
    position: relative;
    font-size: 18px;
    color: #606266;
    left: 70px;
    top: -40px;
  }

  .un-handle-layout {
    margin-top: 20px;
    border: 1px solid #DCDFE6;
  }

  .layout-title {
    color: #606266;
    padding: 15px 20px;
    background: #F2F6FC;
    font-weight: bold;
  }

  .un-handle-content {
    padding: 20px 40px;
  }

  .un-handle-item {
    border-bottom: 1px solid #EBEEF5;
    padding: 10px;
  }

  .overview-layout {
    margin-top: 20px;
  }

  .overview-item-value {
    font-size: 24px;
    text-align: center;
  }

  .overview-item-title {
    margin-top: 10px;
    text-align: center;
  }

  .out-border {
    border: 1px solid #DCDFE6;
  }

  .statistics-layout {
    margin-top: 20px;
    border: 1px solid #DCDFE6;
  }
  .mine-layout {
    position: absolute;
    right: 140px;
    top: 107px;
    width: 250px;
    height: 235px;
  }
  .address-content{
    padding: 20px;
    font-size: 18px;
    
  }
</style>
