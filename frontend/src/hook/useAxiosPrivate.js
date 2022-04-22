import axios from "../api/axios.js";
import {axiosPrivate} from "../api/axios.js";
import {useEffect} from "react";
import useAuth from "./useAuth.js";
import useRefreshToken from "./useRefreshToken.js";
import Cookies from 'js-cookie';

//this hook attaches interceptors into request

const useAxiosPrivate = () => {
    const refresh = useRefreshToken();
    const {auth} = useAuth();

    useEffect(()=> {

        const requestIntercept = axiosPrivate.interceptors.request.use(
            config => {
                const authToken = Cookies.get("token")
                if (authToken)
                {
                    if (!config.headers['Authorization']) {
                        config.headers['Authorization'] = `Bearer ${authToken}`
                    }
                }

                return config;
            }, (err) => Promise.reject(err)
        );

        const responseIntercept = axiosPrivate.interceptors.response.use(
            //if response is OK, just return a response
            response => response,
            //if something is wrong
            async (err) =>{
                //get a previous request
                const prevRequest = err?.config;
                //if request is failed due to an expires token
                if (err.response?.status === 403 && !prevRequest?.sent) {
                    prevRequest.sent = true;
                    const newAccessToken = await refresh();
                    prevRequest.headers['Authorization'] = `Bearer ${newAccessToken}`;
                    return axiosPrivate(prevRequest);
                }
                return Promise.reject(err);
            }
        );

        return () => {
            axiosPrivate.interceptors.request.eject(requestIntercept);
            axiosPrivate.interceptors.response.eject(responseIntercept);
        }
    }, [auth, refresh])

    return axiosPrivate;
}

export default useAxiosPrivate;