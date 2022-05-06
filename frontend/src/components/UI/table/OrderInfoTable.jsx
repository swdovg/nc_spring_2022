import React, {useEffect, useRef, useState} from 'react';
import './Table.css';
import '../../../styles/bootstrap.min.css';
import useRefreshToken from "../../../hook/useRefreshToken.js"
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js"
import { useNavigate, useLocation } from "react-router-dom";
import Modal from '../modal/Modal.jsx';
import Button from '../button/Button.jsx';
import DeleteModal from '../deleteModal/DeleteModal.jsx';
import Subscription from './Subscription.jsx';
import EditSubscriptionModal from '../editSubscriptionModal/EditSubscriptionModal.jsx';
import Cookies from 'js-cookie';


const OrderInfoTable = (props) => {

    const [consumers, setConsumers] = useState([{}]);
    const axiosPrivate = useAxiosPrivate();

    useEffect( () => {
        setConsumers(props.consumers);
    },[props.consumers] )

    return (
        <table className="user_table col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
            <thead>
                <tr className="table_heading">
                    <th className="table_heading_item col-xl-3 col-lg-3">consumer:</th>
                    <th className="table_heading_item col-xl-3 col-lg-3">email:</th>
                    <th className="table_heading_item col-xl-2 col-lg-2">phone:</th>
                    <th className="table_heading_item col-xl-3 col-lg-3">address:</th>
                </tr>
            </thead>
            <tbody>
                {consumers.length>0
                ? (
                    <>
                        {consumers?.map((item, i) =>
                            <tr className="table_row" key={i}>
                                <td className="table_cont_item col-xl-2 col-lg-2">{item.consumer?.name}</td>
                                <td className="table_cont_item col-xl-2 col-lg-2">{item.consumer?.email}</td>
                                <td className="table_cont_item col-xl-2 col-lg-2">{item.consumer?.phoneNumber}</td>
                                <td className="table_cont_item col-xl-6 col-lg-6">{item.consumer?.defaultLocation.location}</td>
                            </tr>
                        )}
                    </>
                ) : <tr><td>You have no orders</td></tr>
            }
            </tbody>
        </table>
    )
};

export default OrderInfoTable;