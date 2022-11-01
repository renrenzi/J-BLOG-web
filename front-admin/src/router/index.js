import Vue from 'vue'
import VueRouter from 'vue-router'

NProgress.configure({
    easing: 'ease', // 动画方式
    speed: 400, // 递增进度条的速度
    showSpinner: false, // 是否显示加载ico
    trickleSpeed: 200, // 自动递增间隔
    minimum: 0.3 // 初始化时的最小百分比
});
Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/admin/login/login'),
        hidden: true
    },
    {
        path: '/',
        meta: {
            requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
        },
        hidden: true,
        component: () => import('../views/admin/layout/Admin'),
        redirect: '/adminHome',
        children: [
            {
                path: '/adminHome',
                name: '主页',
                component: () => import('../views/admin/layout/AdminHome'),
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            },
        ]
    },
    {
        path: '/blogManager',
        name: '文章管理',
        meta: {
            requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
        },
        component: () => import('../views/admin/layout/Admin'),
        children: [
            {
                path: '/editBlog',
                name: '文章编辑',
                component: () => import('../views/admin/blog/info/EditBlog'),
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            },
            {
                path: '/blogList',
                name: '文章列表',
                component: () => import('../views/admin/blog/info/BlogList'),
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            },
            {
                path: '/commentList',
                name: '评论列表',
                component: () => import('../views/admin/blog/info/CommentList'),
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },

            }
        ]
    },
    {
        path: '/labelManager',
        name: '标签管理',
        component: () => import('../views/admin/layout/Admin'),
        meta: {
            requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
        },
        children: [
            {
                path: '/labelList',
                name: '标签列表',
                component: () => import('../views/admin/blog/label/LabelList'),
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            }
        ]
    },
    {
        path: '/classifyManager',
        name: '分类管理',
        component: () => import('../views/admin/layout/Admin'),
        meta: {
            requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
        },
        children: [
            {
                path: '/GroupList',
                name: '分类列表',
                component: () => import('../views/admin/blog/category/CategoryList'),
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            }
        ]
    },
    {
        path: '/authManager',
        name: '权限管理',
        component: () => import('../views/admin/layout/Admin'),
        meta: {
            requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
        },
        children: [
            {
                path: '/userManager',
                name: '用户管理',
                component: () => import('../views/admin/auth/user/UserList'),
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            },
            {
                path: '/roleManager',
                name: '角色管理',
                component: () => import('../views/admin/auth/role/RoleList'),
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            },
            {
                path: '/resourceManager',
                name: '资源管理',
                component: () => import('../views/admin/auth/resource/ResourceList'),
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            },
            {
                path: '/resourceCategory',
                name: '资源分类管理',
                hidden: true,
                component: () => import('../views/admin/auth/resource/ResourceCategoryList'),
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                }
            }
        ]
    },
    {
        path: '/systemManager',
        name: '系统管理',
        component: () => import('../views/admin/layout/Admin'),
        meta: {
            requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
        },
        children: [
            {
                path: '/SystemMessage',
                name: '系统信息',
                component: () => import('../views/admin/system/ConfigList'),
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            },
            {
                path: '/SystemList',
                name: '链接列表',
                component: () => import('../views/admin/system/LinkList'),
                meta: {
                    requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
                },
            },
        ]
    }

]

const router = new VueRouter({
    mode: 'history',
    routes
})

router.beforeEach((to, from, next) => {
    NProgress.start();
    if (to.meta.requireAuth) { // 判断该路由是否需要登录权限
        if (localStorage.getItem('token')) { // 通过vuex state获取当前的token是否存在
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

router.afterEach(() => {
    NProgress.done();
});

export default router
