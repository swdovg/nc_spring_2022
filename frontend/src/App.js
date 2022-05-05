import React, {useContext, useState, useEffect} from 'react';
import AppRouter from "./components/AppRouter.jsx";
import Cookies from 'js-cookie';
import {AuthContext} from './context/AuthContext.js';
import useAuth from './hook/useAuth.js';

function App() {
   const {auth, setAuth} = useAuth();

    useEffect(() => {
        try{
            setAuth(JSON.parse(Cookies.get("user")));
        }
        catch (err) {
            console.log(err.message);
        }
    }, []);

    return (
         <AppRouter/>
    )
}
export default App;