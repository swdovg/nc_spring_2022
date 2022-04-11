import React from 'react';
import './Table.css';
import '../../../styles/bootstrap.min.css';

const Table = ({heading}) => {
    return (
        <table className="user_table col-xl-8 col-lg-8 col-md-12 col-sm-12 col-12">
            <tr className="table_heading">
                <th className="table_heading_item col-xl-6 col-lg-6">service:</th>
                <th className="table_heading_item col-xl-3 col-lg-3">price: USD/month</th>
                <th className="table_heading_item col-xl-3 col-lg-3">{heading}</th>
            </tr>
            <tr className="table_row">
                <td className="table_cont_item col-xl-6 col-lg-6">Name of subscription</td>
                <td className="table_cont_item col-xl-3 col-lg-3">$ 20</td>
                <td className="table_cont_item col-xl-3 col-lg-3">14th</td>
            </tr>
            <tr className="table_row">
                <td className="table_cont_item col-xl-6 col-lg-6">Name of subscription</td>
                <td className="table_cont_item col-xl-3 col-lg-3">$ 20</td>
                <td className="table_cont_item col-xl-3 col-lg-3">14th</td>
            </tr>
            <tr className="table_row">
                <td className="table_cont_item col-xl-6 col-lg-6">Name of subscription</td>
                <td className="table_cont_item col-xl-3 col-lg-3">$ 20</td>
                <td className="table_cont_item col-xl-3 col-lg-3">14th</td>
            </tr>
            <tr className="table_row">
                <td className="table_cont_item col-xl-6 col-lg-6">Name of subscription</td>
                <td className="table_cont_item col-xl-3 col-lg-3">$ 20</td>
                <td className="table_cont_item col-xl-3 col-lg-3">14th</td>
            </tr>
            <tr className="table_row">
                <td className="table_cont_item col-xl-6 col-lg-6">Name of subscription</td>
                <td className="table_cont_item col-xl-3 col-lg-3">$ 20</td>
                <td className="table_cont_item col-xl-3 col-lg-3">14th</td>
            </tr>
            <tr className="table_row">
                <td className="table_cont_item col-xl-6 col-lg-6">Name of subscription</td>
                <td className="table_cont_item col-xl-3 col-lg-3">$ 20</td>
                <td className="table_cont_item col-xl-3 col-lg-3">14th</td>
            </tr>
            <tr className="table_row">
                <td className="table_cont_item col-xl-6 col-lg-6">Name of subscription</td>
                <td className="table_cont_item col-xl-3 col-lg-3">$ 20</td>
                <td className="table_cont_item col-xl-3 col-lg-3">14th</td>
            </tr>
            <tr className="table_row">
                <td className="table_cont_item col-xl-6 col-lg-6">Name of subscription</td>
                <td className="table_cont_item col-xl-3 col-lg-3">$ 20</td>
                <td className="table_cont_item col-xl-3 col-lg-3">14th</td>
            </tr>
            <tr className="table_row">
                <td className="table_cont_item col-xl-6 col-lg-6">Name of subscription</td>
                <td className="table_cont_item col-xl-3 col-lg-3">$ 20</td>
                <td className="table_cont_item col-xl-3 col-lg-3">14th</td>
            </tr>
        </table>

    );
};

export default Table;