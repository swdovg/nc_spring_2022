import React, {useContext, useState, useEffect} from 'react';
import '../styles/view.css';
import '../styles/style.css';
import '../styles/bootstrap.min.css';
import Button from '../components/UI/button/Button';
import Header from '../components/header/Header.jsx';
import Footer from '../components/footer/Footer.jsx';
import Modal from '../components/UI/modal/Modal.jsx';
import Table from '../components/UI/table/Table.jsx';
import AddButton from '../components/UI/addButton/AddButton.jsx';
import UserInfo from '../components/UI/userInfo/UserInfo.jsx';
import {BrowserRouter, Routes ,Route,Link} from "react-router-dom";
import axios from "axios";
import Cookies from 'js-cookie';


function CustomerView ()  {

    const [modalVisible, setModalVisible] = useState(false);
    const [subscriptions, setSubscription] = useState([]);
    //const role = JSON.parse(Cookies.get("user")).role;

    const createSubscription = (newSubscription) => {
            setSubscription([...subscriptions, newSubscription])
    }

    async function fetchSubscriptions(){
        const response = await axios.get("http://localhost:8080/api/v1/subscription");
        setSubscription(...subscriptions, ...response.data);
    }

    const [amount, setAmount] = useState(0);

    const updateAmount = (value) => {
       setAmount(value);
    }

    return (
        <div>
            <Header />
            <div className="container cont">
                <div className="row heading">
                    <h1 className="heading_text col-xl-7 col-lg-7 col-md-8 col-sm-8 col-8">Subscriptions:</h1>
{/*                     {role==="ROLE_CONSUMER"
                        ? */}
                            <Link to="/">
                                <AddButton className="col-xl-2 col-lg-2 col-md-4 col-sm-4 col-4 float-lg-right"> add</AddButton>
                            </Link>
{/*                          :
                        <> </>
                    } */}
                </div>
                <div className="row ">
                    <Table heading="date of payment:" subscriptions={subscriptions} updateAmount={updateAmount}/>
                    <UserInfo amount={amount}/>
                </div>
            </div>
            <Footer />
        </div>
        );
    };

export default CustomerView;