import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '../store'
Vue.use(VueRouter)

const routes = [

    {
        path: '/admin',
        name: '文章管理',
        meta: {
            requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
        },
        component: () => import('../views/admin/Admin'),
        redirect: '/adminHome',
        children: [
            {
                path: '/adminHome',
                name: '主页',
                component: () => import('../views/admin/AdminHome'),
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            },
            {
                path: '/editBlog',
                name: '文章编辑',
                component: () => import('../views/admin/blogmanager/EditBlog'),
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            },
            {
                path: '/blogList',
                name: '文章列表',
                component: () => import('../views/admin/blogmanager/BlogList'),
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            },
            {
                path: '/commentList',
                name: '评论列表',
                component: () => import('../views/admin/blogmanager/CommentList'),
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },

            }
        ]
    },
    {
        path: '/labelManager',
        name: '标签管理',
        component: () => import('../views/admin/Admin'),
        meta: {
            requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
        },
        children: [
            {
                path: '/labelList',
                name: '标签列表',
                component: () => import('../views/admin/labelmanager/LabelList'),
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            }
        ]
    },
    {
        path: '/classifyManager',
        name: '分类管理',
        component: () => import('../views/admin/Admin'),
        meta: {
            requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
        },
        children: [
            {
                path: '/GroupList',
                name: '分类列表',
                component: () => import('../views/admin/classfymanager/GroupList'),
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            }
        ]
    },
    {
        path: '/systemManager',
        name: '系统管理',
        component: () => import('../views/admin/Admin'),
        meta: {
            requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
        },
        children: [
            {
                path: '/SystemMessage',
                name: '系统信息',
                component: () => import('../views/admin/systemmanager/SystemMessage'),
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            },
            {
                path: '/SystemList',
                name: '链接列表',
                component: () => import('../views/admin/systemmanager/SystemList'),
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            },
        ]
    },
    {
        path: '/',
        name: '主页',
        component: () => import('../views/blog/Home'),
        redirect: '/index',
        children: [
            {
                path: '/index',
                name: 'Index',
                component: () => import('../components/blog/Index')
            },
            {
                path: '/blogDetail',
                name: '文章详情',
                component: () => import('../components/blog/BlogDetail'),
            }
        ]
    },
    {
        path: '/tomo',
        name: '友链',
        component: () => import('../views/blog/Tomo')
    },
    {
        path: '/messageBoard',
        name: '留言',
        component: () => import('../views/blog/MessageBoard')
    },
    {
        path: '/about',
        name: '关于',
        component: () => import('../views/blog/About')
    },

    {
        path: '/login',
        name: 'Login',
        component:  () => import('../views/admin/login')
    },


]

const router = new VueRouter({
    mode: 'history',
    routes
})

router.beforeEach((to, from, next) => {
    if (to.meta.requireAuth) { // 判断该路由是否需要登录权限
        console.info(store.state.token);
        if (store.state.token) { // 通过vuex state获取当前的token是否存在
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
