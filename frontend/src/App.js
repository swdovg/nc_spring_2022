import React, {useContext} from 'react';
import Sign from './pages/Sign.jsx';
import Login from './pages/Login.jsx';
import Error from './pages/Error.jsx';
import Main from './pages/Main.jsx';
import EditConsumer from './pages/EditConsumer.jsx';
import EditSupplier from './pages/EditSupplier.jsx';
import CustomerView from './pages/CustomerView.jsx';
import {BrowserRouter, Routes ,Route,Link} from "react-router-dom";

function App() {

    return (
        <BrowserRouter>
             <Routes>
                  <Route path="/sign" element={<Sign/>} />
                  <Route path="/login" element={<Login/>} />
                  <Route path="/edit-consumer" element={<EditConsumer/>} />
                  <Route path="/edit-supplier" element={<EditSupplier/>} />
                  <Route path="/main" element={<Main/>} />
                  <Route path="/" element={<Main/>} />
                  <Route path="/customer-profile" element={<CustomerView/>} />
              </Routes>
        </BrowserRouter>
    )
}
export default App;