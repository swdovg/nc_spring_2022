import React, {useContext} from 'react';
import '../styles/login.css';
import '../styles/bootstrap.min.css';
import Input from "../components/UI/input/Input";
import Button from "../components/UI/button/Button";
import sign from '../img/sign_img.png';
import google from '../img/google.svg';
import Header from '../components/header/Header.jsx';
import SignForm from '../components/UI/signForm/SignForm.jsx';
import Footer from '../components/footer/Footer.jsx';
import {BrowserRouter, Routes ,Route,Link} from "react-router-dom";


const Sign = () => {

    return (
            <div>
                <Header />
                <div className="container">
                    <div className="row  login-cont">
                        <div className="col-xl-8 col-lg-7 c d-sm-none d-none d-md-none d-lg-block login-img">
                            <img src={sign} alt="People look on laptop" className="login-img"/>
                        </div>
                        <div className="col-xl-4 col-lg-5 col-md-12 login-form">
                            <h1 className="login-heading">Create account</h1>
                            <SignForm method="post" />
                        </div>
                    </div>
                    <div className="row">
                        <div className="offset-xl-8 offset-lg-8 col-xl-4 col-lg-4 col-md-12  col-sm-12 col-12 login-bottom">
                            <p className="login-bottom-text">Do have an account?</p>
                            <Link to="/login" className="login-bottom-link"> Log in now</Link>
                        </div>
                    </div>
                </div>
                <Footer />
            </div>
        );
    };

export default Sign;