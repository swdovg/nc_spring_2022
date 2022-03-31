import React, {useContext} from 'react';
import Sign from './pages/Sign.jsx';
import Login from './pages/Login.jsx';
import Error from './pages/Error.jsx';
import EditConsumer from './pages/EditConsumer.jsx';
import EditSupplier from './pages/EditSupplier.jsx';
import {BrowserRouter, Redirect, Route, Switch} from 'react-router-dom';

const App = () => {

    return (
        <Login/>
/*
        <BrowserRouter>
            <Route path="/sign">
                <Sign />
            </Route>
            <Route path="/login">
                <Login />
            </Route>
        </BrowserRouter>
*/
    );
};

export default App;