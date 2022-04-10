import Vue from 'vue'
import Vuex from 'vuex'

import createVuexAlong from 'vuex-along'

Vue.use(Vuex)



export default new Vuex.Store({
    state: {
        aboutId: 33,
        adminUser: {
            loginUserName: '',
        },
        token: '',
        imgUrl: '',
        userUrl: 'http://120.39.217.37:2234/group1/M00/00/00/rB8AYWI0ku-AKTJpAAAadI1c_Xc036.png'
    },
    mutations: {
        adminUser: function (state, adminUser) {
            state.adminUser = adminUser;
        }
    },
    actions: {},
    modules: {
    },
    plugins: [createVuexAlong()]
})
