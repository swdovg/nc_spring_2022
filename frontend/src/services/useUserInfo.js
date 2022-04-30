import React, {useContext, useState, useEffect} from 'react';
import {BrowserRouter, Routes ,Route,Link} from "react-router-dom";
import {AuthContext} from '../context/AuthContext.js';
import Cookies from 'js-cookie';
import { useNavigate, useLocation } from "react-router-dom";
import useAxiosPrivate from "../hook/useAxiosPrivate.js"
import useAuth from "../hook/useAuth.js";

async function useUserInfo () {

    const [phoneNumber, setPhoneNumber] = useState("");
    const axiosPrivate = useAxiosPrivate();
    const [name, setName] = useState("");
    const [id, setId] = useState("");
    const [defaultLocation, setDefaultLocation] = useState({});
    const [locations, setLocations] = useState([]);
    const [currency, setCurrency] = useState("");
    const [role, setRole] = useState("");

    useEffect( () => {
        let isMounted = true;
        const controller = new AbortController(); //to cansel request if the component on mounting

        const getInfo = async () => {
            try {
                let response = await axiosPrivate.get("api/v1/user", {
                    signal: controller.signal      //to allow to cansel a request
                });
                isMounted && setName(response.data.payload.name);
                isMounted && setId(response.data.payload.id);
                isMounted && setPhoneNumber(response.data.payload.phoneNumber);
                isMounted && setDefaultLocation(response.data.payload.defaultLocation);
                isMounted && setCurrency(response.data.payload.currency);
                isMounted && setRole(response.data.payload.role);

                 response = await axiosPrivate.get(
                 "/api/v1/user/location",
                 {
                    signal: controller.signal      //to allow to cansel a request
                });
                isMounted && setLocations(response.data.payload);
            } catch(err) {
                console.log(err);
            }
        }
        getInfo();
        return () =>{
            isMounted=false;
            controller.abort();
        }
    });

    return {name, id, phoneNumber, defaultLocation, currency, role, locations};
}

export default useUserInfo;