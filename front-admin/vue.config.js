
//const CompressionPlugin = require('compression-webpack-plugin')


const isProduction = process.env.NODE_ENV === 'production';
const cdn = {
    js: [
        'https://cdn.bootcss.com/vue/2.5.17/vue.runtime.min.js',
        'https://cdn.bootcss.com/vue-router/3.0.1/vue-router.min.js',
        'https://cdn.bootcss.com/vuex/3.0.1/vuex.min.js',
        'https://cdn.bootcss.com/axios/0.18.0/axios.min.js',
        "https://cdn.bootcdn.net/ajax/libs/element-ui/2.15.6/index.js",
        "https://cdn.bootcdn.net/ajax/libs/nprogress/0.2.0/nprogress.min.js"
    ],
    css: [
        "https://unpkg.com/element-ui@2.15.6/lib/theme-chalk/index.css",
        "https://cdn.bootcdn.net/ajax/libs/nprogress/0.2.0/nprogress.min.css"
    ],
}
module.exports = {

    devServer:{
      https: false
    },
    productionSourceMap: false, // 是否在构建生产包时生成 sourceMap 文件，false将提高构建速度
    chainWebpack: config => {
        // 生产环境配置
      //  if (isProduction) {
            // 生产环境注入cdn
            config.plugin('html')
                .tap(args => {
                    args[0].cdn = cdn;
                    return args;
                });
       // }
    },
    configureWebpack: config => {
      //  if (isProduction) {
            // 用cdn方式引入
            config.externals = {
                'vue': 'Vue',
                'vuex': 'Vuex',
                'vue-router': 'VueRouter',
                'axios': 'axios',
                "element-ui": "ElementUI",
                nprogress: "NProgress"
            }
        //}
    },

}
