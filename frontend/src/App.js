import React, {useContext, useState} from 'react';
import {BrowserRouter, Routes ,Route,Link} from "react-router-dom";
import AppRouter from "./components/AppRouter.jsx";
import { useForm } from "react-hook-form";

function App() {

    const [isAuth, setIsAuth] = useState(false);

    return (
         <AppRouter/>
    )
}
export default App;