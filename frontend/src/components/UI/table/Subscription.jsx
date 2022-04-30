import React, {useEffect, useRef, useState} from 'react';
import './Table.css';
import '../../../styles/bootstrap.min.css';
import useRefreshToken from "../../../hook/useRefreshToken.js"
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js"
import { useNavigate, useLocation } from "react-router-dom";
import Modal from '../modal/Modal.jsx';
import OrderInfoTable from './OrderInfoTable.jsx';
import Button from '../button/Button.jsx';
import DeleteModal from '../deleteModal/DeleteModal.jsx';
import EditSubscriptionModal from '../editSubscriptionModal/EditSubscriptionModal.jsx';
import Cookies from 'js-cookie';


const Subscription = (props)=>{

    const [modalVisible, setDeleteModalVisible] = useState(false);
    const [editModalVisible, setEditModalVisible] = useState(false);
    const [infoModalVisible, setInfoModalVisible] = useState(false);
    const role = JSON.parse(Cookies.get("user")).role;
    const axiosPrivate = useAxiosPrivate();
    const [subscription, setSubscription] = useState({});
    const [consumers, setConsumers] = useState([]);

    const getSubscription=() => {
        setSubscription(props.subscription);
        setEditModalVisible(true);
        console.log(props.description);
    }

    const getConsumers = () => {
        let isMounted = true;
        const controller = new AbortController(); //to cansel request if the component on mounting
        const URL = `/api/v1/order/${props.id}`;
        const getOrderInfo = async () => {
            try {
                const response = await axiosPrivate.get(URL, {
                    signal: controller.signal      //to allow to cansel a request
                });
                setConsumers(response.data?.payload.content);
                console.log(consumers);
            } catch(err) {
                console.log(err);
            }
        }
        getOrderInfo();

        setInfoModalVisible(true);
        return () =>{
            isMounted=false;
            controller.abort();
        }
    }

    return(
        <tr className="table_row" >
            <td className="table_cont_item col-xl-7 col-lg-7">{props.title}</td>
            <td className="table_cont_item col-xl-5 col-lg-5">{props.price} {props.currency}
                <button className="info_btn" onClick = {getConsumers}/>
                <button className="edit_btn" onClick={getSubscription}/>
                <button className="remove_btn" onClick={()=>setDeleteModalVisible(true)}/>
            </td>

            <DeleteModal orderId={props.id} visible = {modalVisible} setVisible={setDeleteModalVisible}/>
            <EditSubscriptionModal
                id={props.id}
                visible = {editModalVisible}
                setVisible={setEditModalVisible}
                title={props.title}
                price={props.price}
                description={props.description}
                currency={props.currency}
                category={props.category?.name}
            />
             <Modal visible={infoModalVisible} setVisible={setInfoModalVisible}>
                <OrderInfoTable consumers={consumers}/>
            </Modal>
        </tr>
    )
};

export default Subscription;