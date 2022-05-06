import React from 'react';
//import cl from './ProductCard.module.css';
import { useNavigate } from 'react-router-dom';
import '../../../styles/bootstrap.min.css';
import Button from '../button/Button';
import Cookies from 'js-cookie';
import Modal from '../modal/Modal.jsx';
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js";

const DeleteModal = (props) => {

    const role = JSON.parse(Cookies.get("user")).role;
    const axiosPrivate = useAxiosPrivate();
    const navigate = useNavigate();

    const deleteSubscription = () => {
        props.deleted(true);
        console.log("deleted")
        let isMounted = true;
        const controller = new AbortController(); //to cansel request if the component on mounting
        let URL = `api/v1/subscription/${props.orderId}`;
        if (role === "ROLE_CONSUMER")
            URL = `api/v1/order/${props.orderId}`;
        const deleteSubscription = async () => {
            try {
                const response = await axiosPrivate.delete(URL, {
                    signal: controller.signal      //to allow to cansel a request
                });

            } catch(err) {
                console.log(err);
            }
        }
        deleteSubscription();
        props.setVisible(false);
        return () =>{
            isMounted=false;
            controller.abort();
        }
    }

    return (
        <Modal visible={props.visible} setVisible={props.setVisible}>
            <p>Do you want to delete subscription?</p>
            <Button style={{width:'60px', display: 'inline-block'}} onClick={deleteSubscription}>Yes </Button>
            <Button style={{width:'60px', display: 'inline-block'}} onClick={()=>props.setVisible(false)}>No </Button>
        </Modal>

    );
};

export default DeleteModal;