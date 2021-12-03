import axios from "axios";

export const getConfigList = () => {
    return axios.get('/config/getConfigList')
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
export const addConfig = (blogConfig) => {
    return axios.post('/config/addConfig',blogConfig)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
export const updateConfig = (blogConfig) => {
    return axios.post('/config/updateConfig',blogConfig)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}
export const deleteConfig = (configField) => {
    return axios.post('/config/deleteConfig',configField)
        .then(res => {
            return res.data;
        })
        .catch(res => {
            return false;
        })
}

