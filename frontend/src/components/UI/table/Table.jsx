import React, {useEffect, useRef, useState} from 'react';
import './Table.css';
import '../../../styles/bootstrap.min.css';
import useRefreshToken from "../../../hook/useRefreshToken.js"
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js"
import { useNavigate, useLocation } from "react-router-dom";

const Subscription = (props)=>{
    return(
        <tr className="table_row">
            <td className="table_cont_item col-xl-6 col-lg-6">{props.title}</td>
            <td className="table_cont_item col-xl-3 col-lg-3">{props.price}</td>
            <td className="table_cont_item col-xl-3 col-lg-3">{props.date}</td>
        </tr>
    )
};

const Table = (props) => {

    const [subscriptions, setSubscriptions] = useState();
    const axiosPrivate = useAxiosPrivate();
    const navigate = useNavigate();
    const location = useLocation();

    /* useEffect( () => {

         let isMounted = true;
         const controller = new AbortController(); //to cansel request if the component on mounting

         const getSubscriptions = async () => {
             try {
                 const response = await axiosPrivate.get("/api/v1/subscription?page=1&size=2&sort=id", {
                     signal: controller.signal      //to allow to cansel a request
                 });
                 isMounted && setSubscriptions(response.data.payload.content);
             } catch(err) {
                 console.log(err);
                 //navigate('/login', { state: { from: location }, replace: true });
             }
         }
         getSubscriptions();

         return () =>{
             isMounted=false;
             controller.abort();
         }
     }, []); */

    return (
    <>
        <table className="user_table col-xl-8 col-lg-8 col-md-12 col-sm-12 col-12">
            <thead>
                <tr className="table_heading">
                    <th className="table_heading_item col-xl-6 col-lg-6">service:</th>
                    <th className="table_heading_item col-xl-3 col-lg-3">price:</th>
                    <th className="table_heading_item col-xl-3 col-lg-3">{props.heading}</th>
                </tr>
            </thead>
            <tbody>
                {subscriptions?.length
                ? (
                    <>
                        {subscriptions.map((subscription, i) => <Subscription key={i}/>)}
                    </>
                ) : <tr><td></td></tr>
            }

            </tbody>
        </table>
        </>
    )
};

export default Table;