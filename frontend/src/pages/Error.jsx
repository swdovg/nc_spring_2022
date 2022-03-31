import React, {useContext} from 'react';
import '../styles/error.css';
import '../styles/bootstrap.min.css';
import Input from "../components/UI/input/Input";
import error from '../img/error.png';
import google from '../img/google.svg';
import Header from '../components/header/Header.jsx';
import Footer from '../components/footer/Footer.jsx';
import LoginForm from '../components/UI/loginForm/LoginForm.jsx';

const Error = () => {

    return (
                <div className="error">
                    <Header />
                    <div className="container">
                        <div className="row  error-cont">
                            <div className="col-xl-8 col-lg-7 c d-sm-none d-none d-md-none d-lg-block error-img">
                                <img src={error} alt="People look on laptop" className="error-img"/>
                            </div>
                            <div className="col-xl-4 col-lg-5 col-md-12 ">
                                <p className="error-text">Something went wrong</p>
                                <h1 className="error-heading">This page doesn't exist</h1>
                            </div>
                        </div>

                    </div>
                    <Footer />
                </div>

            );
        };

export default Error;