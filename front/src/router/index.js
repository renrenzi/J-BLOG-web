import Vue from 'vue'
import VueRouter from 'vue-router'
import Admin from "../views/admin/Admin";
import EditBlog from "../views/admin/blogmanager/EditBlog";
import BlogList from "../views/admin/blogmanager/BlogList";
import CommentList from "../views/admin/blogmanager/CommentList";
import LabelList from "../views/admin/labelmanager/LabelList";
import GroupList from "../views/admin/classfymanager/GroupList";
import SystemMessage from "../views/admin/systemmanager/SystemMessage";
import SystemList from "../views/admin/systemmanager/SystemList";
import store from '../store'
import Index from '../components/blog/Index'
import About from "../views/blog/About";
import Home from "../views/blog/Home";
import login from "../views/admin/login";
import AdminHome from "../views/admin/AdminHome";
import Tomo from "../views/blog/Tomo";
import BlogDetail from "../components/blog/BlogDetail";

Vue.use(VueRouter)

const routes = [

    {
        path: '/admin',
        name: '文章管理',
        meta: {
            requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
        },
        component: Admin,
        redirect: '/adminHome',
        children: [
            {
                path: '/adminHome',
                name: '主页',
                component: AdminHome,
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            },
            {
                path: '/editBlog',
                name: '文章编辑',
                component: EditBlog,
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            },
            {
                path: '/blogList',
                name: '文章列表',
                component: BlogList,
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            },
            {
                path: '/commentList',
                name: '评论列表',
                component: CommentList,
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },

            }
        ]
    },
    {
        path: '/labelManager',
        name: '标签管理',
        component: Admin,
        meta: {
            requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
        },
        children: [
            {
                path: '/labelList',
                name: '标签列表',
                component: LabelList,
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            }
        ]
    },
    {
        path: '/classifyManager',
        name: '分类管理',
        component: Admin,
        meta: {
            requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
        },
        children: [
            {
                path: '/GroupList',
                name: '分类列表',
                component: GroupList,
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            }
        ]
    },
    {
        path: '/systemManager',
        name: '系统管理',
        component: Admin,
        meta: {
            requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
        },
        children: [
            {
                path: '/SystemMessage',
                name: '系统信息',
                component: SystemMessage,
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            },
            {
                path: '/SystemList',
                name: '链接列表',
                component: SystemList,
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            },
        ]
    },
    {
        path: '/',
        name: '主页',
        component: Home,
        redirect: '/index',
        children: [
            {
                path: '/index',
                name: 'Index',
                component: Index
            },
            {
                path: '/blogDetail',
                name: '文章详情',
                component: BlogDetail,
            }
        ]
    },
    {
        path: '/tomo',
        name: '友链',
        component: Tomo
    },
    {
        path: '/about',
        name: '关于',
        component: About
    },
    {
        path: '/login',
        name: 'Login',
        component: login
    }

]

const router = new VueRouter({
    mode: 'history',
    routes
})

router.beforeEach((to, from, next) => {
    if (to.meta.requireAuth) { // 判断该路由是否需要登录权限
        if (store.state.adminUser.loginUserName) { // 通过vuex state获取当前的token是否存在
            next();
        } else {
            next({
                path: '/login',
            })
        }
    } else {
        next();
    }
})

export default router
