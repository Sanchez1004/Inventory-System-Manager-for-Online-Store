import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: "http://192.168.1.31:8080",
    timeout: 5000,
});

export default axiosInstance;
