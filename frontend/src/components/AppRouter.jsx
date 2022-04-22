import React, {useContext} from 'react';
import {BrowserRouter, Routes ,Route,Link, Redirect} from "react-router-dom";
import {AuthContext} from '../context/AuthContext.js';
import RequireAuth from "./RequireAuth.js";
import Sign from '../pages/Sign.jsx';
import Login from '../pages/Login.jsx';
import Error from '../pages/Error.jsx';
import Main from '../pages/Main.jsx';
import EditUser from '../pages/EditUser.jsx';
import CustomerView from '../pages/CustomerView.jsx';

const ROLES= {
    supplier: "ROLE_SUPPLIER",
    consumer: "ROLE_CONSUMER",
}

const AppRouter = () => {

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/login" element= {<Login/>}/>
                <Route path="/sign" element= {<Sign/>}/>
                <Route path="/" element= {<Main/>}/>
                <Route element = {<RequireAuth allowedRole = {[ROLES.consumer, ROLES.supplier]}/>}>
                    <Route path="/customer-profile" element= {<CustomerView/>}/>
                    <Route path="/edit-user" element= {<EditUser/>}/>
                </Route>
            </Routes>
        </BrowserRouter>
        );
};

export default AppRouter;