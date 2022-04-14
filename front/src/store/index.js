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
        userUrl: 'https://renrenzi.top/imgs/group1/M00/00/00/rB8AYWJUOduASibLAAAarki5D1k853.png'
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
