import React, {useState} from 'react';
import AppRouter from "./components/AppRouter.jsx";

function App() {

    const [isAuth, setIsAuth] = useState(false);

    return (
         <AppRouter/>
    )
}
export default App;