<template>
  <div>
    <el-row style="margin-top: 0px">
      <el-carousel height="320px" direction="vertical" :autoplay="false">
        <el-carousel-item >
          <el-image :src="imgUrl" fit="fill" ></el-image>
        </el-carousel-item>
      </el-carousel>
    </el-row>
    <el-row  id="tagImg">
      <el-menu
          mode="horizontal"
          text-color="#000000"
          :router=true
      >
        <el-menu-item index="home"
                      v-for="(item,index) in $router.options.routes"
                      :index="item.path"
                      v-if="item.name.length === 2">
          <span>{{ item.name }}</span>
        </el-menu-item>
        <el-menu-item>
          <a href="https://github.com/Zinner2" target="_blank">GITHUB</a>
        </el-menu-item>
        <el-menu-item>
          <router-link to="admin">管理</router-link>
        </el-menu-item>
      </el-menu>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "BlogHead",
  data() {
    return {
      imgUrl: "https://renrenzi.top/imgs/group1/M00/00/00/rB8AYWI9vm-AD_vUAAFoAcumkJ4421.png"
    };
  }
}
</script>

<style scoped>
.el-menu--horizontal>.el-menu-item {
  top: -320px;
  z-index: 3;
}
#tagImg{
  margin-top: 0px;
}
.el-container .el-main{
  padding: 0;
}
.el-container .el-footer{
  padding: 0;
}
.el-menu.el-menu--horizontal{
  border: none;
}
.el-menu--horizontal el-menu{
  top: -30px;
}
</style>
