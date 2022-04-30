import React, {useEffect, useRef, useState} from 'react';
import './UserInfo.css';
import '../../../styles/bootstrap.min.css';
import profile_img from '../../../img/user_pic.png';
import setting from '../../../img/setting.png';
import {BrowserRouter, Routes ,Route,Link} from "react-router-dom";
import useRefreshToken from "../../../hook/useRefreshToken.js"
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js"
import { useNavigate, useLocation } from "react-router-dom";
import Cookies from 'js-cookie';

const UserInfo = (props) => {

    const [name, setName] = useState();
    const [user, setUser] = useState({});
    const [role, setRole] = useState();
    const axiosPrivate = useAxiosPrivate();
    const navigate = useNavigate();
    const location = useLocation();

    useEffect( () => {
        setUser(JSON.parse(Cookies.get("user")));
    }, []);


    return (
        <div className="col-xl-4 col-lg-4 d-md-none d-sm-none d-none d-lg-block d-xl-block">
            <div className="userinfo_head">
                <img className="userinfo_head_img" src={profile_img} alt="Profile Image"/>
                <p className="userinfo_head_name"> {user.name} </p>

                {role==="ROLE_SUPPLIER"
                    ?
                    <Link to="/edit-user">
                        <button className="setting_btn float-right" >
                            <img className="setting_btn_img" src={setting} alt="setting"/>
                        </button>
                    </Link>
                    :
                    <Link to="/edit-user">
                        <button className="setting_btn float-right" >
                            <img className="setting_btn_img" src={setting} alt="setting"/>
                        </button>
                    </Link>
                }
            </div>
{/*             <div className="userinfo_info"> */}
{/*                 <p className="userinfo_info_text"> Your monthly profit:</p> */}
{/*                 <p className="userinfo_info_number">150 </p> */}
{/*                 <p className="userinfo_info_currency"> USD/month</p> */}
{/*             </div> */}
            <hr />
            <div className="userinfo_info">
                <p className="userinfo_info_text"> Subcriptions:</p>
                <p className="userinfo_info_number">{props.amount} </p>
                <p className="userinfo_info_currency"> USD/month</p>
            </div>
        </div>

    );
};

export default UserInfo;