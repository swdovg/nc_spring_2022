import React from 'react';
import './UserInfo.css';
import '../../../styles/bootstrap.min.css';
import profile_img from '../../../img/user_pic.png';
import setting from '../../../img/setting.png';
import {BrowserRouter, Routes ,Route,Link} from "react-router-dom";

const UserInfo = ({heading}) => {
    return (
        <div className="col-xl-4 col-lg-4 d-md-none d-sm-none d-none d-lg-block d-xl-block">
            <div className="userinfo_head">
                <img className="userinfo_head_img" src={profile_img} alt="Profile Image"/>
                <p className="userinfo_head_name"> User Name </p>
                <Link to="/edit-consumer ">
                    <button className="setting_btn float-right" >
                        <img className="setting_btn_img" src={setting} alt="setting"/>
                    </button>
                </Link>
            </div>
            <div className="userinfo_info">
                <p className="userinfo_info_text"> Your monthly profit:</p>
                <p className="userinfo_info_number">150 </p>
                <p className="userinfo_info_currency"> USD/month</p>
            </div>
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