import React, {useEffect, useRef, useState} from 'react';
import './UserInfo.css';
import '../../../styles/bootstrap.min.css';
import profile_img from '../../../img/user_pic.png';
import setting from '../../../img/setting.png';
import {BrowserRouter, Routes ,Route,Link} from "react-router-dom";
import useRefreshToken from "../../../hook/useRefreshToken.js"
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js"
import { useNavigate, useLocation } from "react-router-dom";

const UserInfo = ({heading}) => {

    const [name, setName] = useState();
    const [role, setRole] = useState();
    const axiosPrivate = useAxiosPrivate();
    const navigate = useNavigate();
    const location = useLocation();

    useEffect( () => {

        let isMounted = true;
        const controller = new AbortController(); //to cansel request if the component on mounting

        const getUserInfo = async () => {
            try {
                const response = await axiosPrivate.get("api/v1/user", {
                    signal: controller.signal      //to allow to cansel a request
                });
                isMounted && setRole(response.data.payload.role) ;
                isMounted && setName(response.data.payload.name);
                console.log(role==="ROLE_SUPPLIER");
            } catch(err) {
                console.log(err);
                navigate('/login', { state: { from: location }, replace: true });
            }
        }
        getUserInfo();

        return () =>{
            isMounted=false;
            controller.abort();
        }
    }, []);


    return (
        <div className="col-xl-4 col-lg-4 d-md-none d-sm-none d-none d-lg-block d-xl-block">
            <div className="userinfo_head">
                <img className="userinfo_head_img" src={profile_img} alt="Profile Image"/>
                <p className="userinfo_head_name"> {name} </p>

                {role==="ROLE_SUPPLIER"
                    ?
                    <Link to="/edit-supplier">
                        <button className="setting_btn float-right" >
                            <img className="setting_btn_img" src={setting} alt="setting"/>
                        </button>
                    </Link>
                    :
                    <Link to="/edit-consumer">
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
                <p className="userinfo_info_number">50 </p>
                <p className="userinfo_info_currency"> USD/month</p>
            </div>
        </div>

    );
};

export default UserInfo;