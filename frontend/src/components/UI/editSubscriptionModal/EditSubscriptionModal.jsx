import React, {useState, useEffect} from 'react';
import '../../../styles/bootstrap.min.css';
import Button from '../button/Button';
import SubscriptionForm from '../subscriptionForm/SubscriptionForm';
import Cookies from 'js-cookie';
import Modal from '../modal/Modal.jsx';
import useAxiosPrivate from "../../../hook/useAxiosPrivate.js";

const EditSubscriptionModal = (props) => {

    return (
        <Modal visible={props.visible} setVisible={props.setVisible}>
    {/*         <SubscriptionForm
                title={props.title}
                price={props.price}
                description={props.description}
                currency={props.currency}
                category={props.category}
                /> */}
        </Modal>

    );
};

export default EditSubscriptionModal;