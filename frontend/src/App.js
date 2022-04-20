import React, {useContext, useState, useEffect} from 'react';
import AppRouter from "./components/AppRouter.jsx";
import Cookies from 'js-cookie';
import {AuthContext} from './context/AuthContext.js';
import useAuth from './hook/useAuth.js';

function App() {

    return (
         <AppRouter/>
    )
}
export default App;