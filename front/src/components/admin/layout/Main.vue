<template>
  <el-container>
    <el-aside width="200px"
              style="background-color: rgb(238, 241, 246)">
      <el-menu background-color="rgb(238, 241, 246)"

               :collapse="isCollapse"
               router>
        <el-submenu v-for="(item,index) in $router.options.routes"
                    v-if="item.name.length === 4"
                    :index="index + ''">

          <template slot="title">
            <i class="el-icon-user-solid" v-if="index === 0"></i>
            <i class="el-icon-collection-tag" v-if="index === 1"></i>
            <i class="el-icon-reading" v-if="index === 2"></i>
            <i class="el-icon-s-tools" v-if="index === 3"></i>
            {{ item.name }}
          </template>
          <el-menu-item v-for="(item2) in item.children"
                        :index="item2.path"
                        v-if="item2.name.length === 4"
                        @click="addTab(item2.name,item2.path)"
                        :class="item2.path === $route.path ? 'isActive' :''">
            {{ item2.name }}
          </el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>
    <el-main style="background-color: rgb(238, 241, 246)">
      <el-tabs v-model="editableTabsValue" type="card" closable @tab-remove="removeTab" @tab-click="handleClick">
        <el-tab-pane
            v-for="(item, index) in editableTabs"
            :key="item.name"
            :label="item.title"
            :name="item.path"
            type="border-card"
        >
          <span slot="label" v-if="item.title === '主页'"><i class="el-icon-s-home"></i> 主页</span>

        </el-tab-pane>
      </el-tabs>
      <router-view></router-view>
      <!--      <router-view></router-view>-->
    </el-main>
  </el-container>

</template>

<script>

export default {
  name: "Main",
  data() {
    return {
      isCollapse: false,
      editableTabsValue: 'adminHome',
      editableTabs: [{
        title: '主页',
        name: 'adminHome',
        path: 'adminHome'
      }],
      tabIndex: 1
    }
  },
  components: {},
  methods: {
    handleClick(tab, event) {
      if (tab.name !== this.$route.path){
        this.$router.replace(
            {
              path: tab.name
            })
      }

    },
    addTab(targetName, path) {
      let flag = false;
      this.editableTabs.forEach((tab, index) => {
        if (tab.path === path) {
          flag = true;
        }
      })
      if (!flag) {
        this.editableTabs.push({
          title: targetName,
          name: path,
          path: path
        });
        this.editableTabsValue = path;
      }
    },
    removeTab(targetName) {
      if (targetName !== "adminHome"){

        let tabs = this.editableTabs;
        let activeName = this.editableTabsValue;
        if (activeName === targetName) {
          tabs.forEach((tab, index) => {
            if (tab.name === targetName) {
              let nextTab = tabs[index + 1] || tabs[index - 1];
              if (nextTab) {
                activeName = nextTab.name;
                this.$router.push(
                    {
                      path: nextTab.path
                    })
              }
            }
          });
        }
        this.editableTabsValue = activeName;
        this.editableTabs = tabs.filter(tab => tab.name !== targetName);
      }
    }

  }

}
</script>

<style scoped>
.el-aside .el-menu .el-submenu .el-menu-item {
  padding: 0;
}

.el-container .el-main {
  padding: 10px;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
</style>
