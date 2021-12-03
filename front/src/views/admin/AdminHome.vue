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
    <el-main style="background: rgb(255,255,255);height: 170px">
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
    </el-main>
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

</style>
