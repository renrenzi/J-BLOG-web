import axios from "axios";
import qs from "qs";

// 根据环境设定请求后端 url 地址
axios.defaults.baseURL = 'http://120.39.217.37:2233'

/*axios.create({
    baseURL: 'http://localhost:2233',
    retry: 4,
    retryDelay: 1000,
    timeout: 5000, // 超时毫秒数
    responseType: 'json',
// 手动设置请求头
    headers: {
        'Content-Type': 'application/json;'
    }
})*/

/*
// 添加请求拦截器
axios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    if (config.method === 'post' || config.method === 'put') {
        // 在拦截器中设置参数，用JSON的
        config.data =qs.stringify(config.data)
    }
    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 添加响应拦截器
axios.interceptors.response.use(function (response) {
    response = response.data;
    // if (response?.code !== 0) {
    //     return Promise.reject(response);
    // }
    // 对响应数据做点什么
    JSON.parse(response);
    return response;
}, function (error) {
    // 对响应错误做点什么
    return Promise.reject(error);
});
*/
