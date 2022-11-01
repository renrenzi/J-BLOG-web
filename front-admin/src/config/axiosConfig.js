import axios from "axios";

// 根据环境设定请求后端 url 地址
//axios.defaults.baseURL = '//renrenzi.top/api' ===> 上线服务器域名
//axios.defaults.baseURL = 'http://120.39.217.37:2235' ===>  上线服务器后端
//axios.defaults.baseURL = 'http://localhost:2233' ===> 本地
const service = axios.create({
    baseURL: 'http://localhost:2233', // api的base_url
    timeout: 15000 // 请求超时时间
})
// 请求拦截器
service.interceptors.request.use(config => {
    // 让每个请求携带自定义token 请根据实际情况自行修改
    config.headers.token = localStorage.getItem('token')
    //config.headers['token'] = localStorage.getItem('token')
    return config
}, error => {
    // Do something with request error
    console.log(error) // for debug
})

export default service
