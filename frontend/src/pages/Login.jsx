import React, {useContext} from 'react';
import '../styles/login.css';
import '../styles/bootstrap.min.css';
import Input from "../components/UI/input/Input";
import login from '../img/login_img.png';
import google from '../img/google.svg';
import Header from '../components/header/Header.jsx';
import Footer from '../components/footer/Footer.jsx';
import LoginForm from '../components/UI/loginForm/LoginForm.jsx';

const Login = () => {

    return (
            <div>
                <Header />
                <div className="container">
                    <div className="row  login-cont">
                        <div className="col-xl-8 col-lg-7 d-sm-none d-none d-md-none d-lg-block login-img">
                            <img src={login} alt="People look on laptop" className="login-img"/>
                        </div>
                        <div className="col-xl-4 col-lg-5 col-md-12 login-form">
                            <p className="login-text">Welcome back</p>
                            <h1 className="login-heading">Login to your account</h1>
                            <LoginForm action="login-form" method="post" />
                        </div>
                    </div>
                    <div className="row">
                        <div className="offset-xl-8 offset-lg-8 col-xl-4 col-lg-4 col-md-12  col-sm-12 col-12 login-bottom">
                            <p className="login-bottom-text">Dont have an account?</p>
                            <a href="/sign" className="login-bottom-link"> Join free today</a>
                        </div>
                    </div>
                </div>
                <Footer />
            </div>

        );
    };

export default Login;