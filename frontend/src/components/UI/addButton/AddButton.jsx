import React, {useContext, useState} from 'react';
import cl from './AddButton.module.css';
import Button from '../button/Button';

const AddButton = ({children, ...props}) => {

    return (
       <button {...props} className={cl.add_btn}>
            {children}
       </button>
    );
};

export default AddButton;