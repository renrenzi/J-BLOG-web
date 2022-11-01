<template>
    <div style="background-color:rgb(238, 241, 246)">
      <el-container >
        <el-menu background-color="rgb(238, 241, 246)"
                 :collapse="isCollapse"
                 router>
          <el-submenu index="/">
            <template slot="title">
              <i class="el-icon-s-home"></i>
              <span>我的主页</span>
            </template>
          </el-submenu>
          <el-submenu v-for="(item,index) in $router.options.routes"
                      v-if="!item.hidden"
                      :index="index + ''">
            <template slot="title">
              <i class="el-icon-s-management" v-if="index === 1"></i>
              <i class="el-icon-reading" v-if="index === 2"></i>
              <i class="el-icon-collection-tag" v-if="index === 3"></i>
              <i class="el-icon-s-custom" v-if="index === 4"></i>
              <i class="el-icon-share" v-if="index === 5"></i>
              <i class="el-icon-s-tools" v-if="index === 6"></i>
              <span slot="title">{{ item.name }}</span>
            </template>
            <el-menu-item v-for="(item2) in item.children"
                          :index="item2.path"
                          v-if="!item2.hidden"
                          @click="addTab(item2.name,item2.path)"
                          :class="item2.path === $route.path ? 'isActive' :''">
              {{ item2.name }}
            </el-menu-item>
          </el-submenu>
        </el-menu>
          <el-container>
            <el-header>
              <el-tabs v-model="editableTabsValue" type="card" closable @tab-remove="removeTab" @tab-click="handleClick">
                <el-tab-pane :closable="isClosable" name="coll">
                 <span slot="label">
                  <i class="el-icon-s-fold" v-if="isCollapse"></i>
                  <i class="el-icon-s-unfold" v-else></i>
                </span>
                </el-tab-pane>
                <el-tab-pane
                    v-for="(item, index) in editableTabs"
                    :key="item.name"
                    :label="item.title"
                    :name="item.path"
                    type="border-card"
                >
                </el-tab-pane>
              </el-tabs>
            </el-header>


            <router-view></router-view>
          </el-container>


      </el-container>




    </div>





</template>

<script>

export default {
  name: "Main",
  data() {
    return {
      isClosable: true,
      isCollapse: true,
      editableTabsValue: 'adminHome',
      editableTabs: [],
      tabIndex: 1
    }
  },
  methods: {
    handleClick(tab, event) {
      if(tab.name === 'coll'){
        this.isCollapse = !this.isCollapse;
      }else{
        if (tab.name !== this.$route.path){
          this.$router.replace(
              {
                path: tab.name
              })
        }
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
/*.el-menu--collapse .el-submenu__title span {
  display: none;
}

!*隐藏 > *!
.el-menu--collapse .el-submenu__title .el-submenu__icon-arrow {
  display: none;
}*/

</style>
