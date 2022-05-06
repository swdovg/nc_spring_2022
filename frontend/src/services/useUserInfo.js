import React, {useContext, useState, useEffect} from 'react';
import {BrowserRouter, Routes ,Route,Link} from "react-router-dom";
import {AuthContext} from '../context/AuthContext.js';
import Cookies from 'js-cookie';
import { useNavigate, useLocation } from "react-router-dom";
import useAxiosPrivate from "../hook/useAxiosPrivate.js"
import useAuth from "../hook/useAuth.js";

    const useUserInfo = async () => {
        const axiosPrivate = useAxiosPrivate();
        let user;
        let isMounted = true;
        const controller = new AbortController(); //to cansel request if the component on mounting
        try {
            const response = await axiosPrivate.get("api/v1/user", {
                signal: controller.signal      //to allow to cansel a request
            });
            Cookies.set("user", JSON.stringify(response.data?.payload))
        } catch(err) {
            console.log(err);
        }
        isMounted=false;
        controller.abort();
        user = JSON.parse(Cookies.get("user"));

        return user;

    }

export default useUserInfo;