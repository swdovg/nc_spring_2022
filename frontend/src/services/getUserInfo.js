import React, {useContext, useState} from 'react';
import {BrowserRouter, Routes ,Route,Link} from "react-router-dom";
import {AuthContext} from '../context/AuthContext.js';
import Cookies from 'js-cookie';
import { useNavigate, useLocation } from "react-router-dom";
import useAxiosPrivate from "../hook/useAxiosPrivate.js"
import useAuth from "../hook/useAuth.js";

const getUserInfo = () => {


}

export default getUserInfo;