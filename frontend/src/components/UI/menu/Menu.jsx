import React from 'react';
import cl from './Menu.module.css';
import Button from "../button/Button";

const Menu = () => {
    return (
        <div className={cl.menu}>
            <ul class={cl.menu_box}>
                <li className={cl.menu_item}><a className={cl.menu_item_link} href="#">All categories</a></li>
                <li className={cl.menu_item}><a className={cl.menu_item_link} href="#">Technique</a></li>
                <li className={cl.menu_item}><a className={cl.menu_item_link} href="#">Music</a></li>
                <li className={cl.menu_item}><a className={cl.menu_item_link} href="#">Videos</a></li>
                <li className={cl.menu_item}><a className={cl.menu_item_link} href="#">Services</a></li>
            </ul>
          </div>

    );
};

export default Menu;