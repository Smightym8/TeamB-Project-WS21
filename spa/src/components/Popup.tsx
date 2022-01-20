import React from "react";
import { Modal, Button } from 'react-bootstrap';

interface Props {
    content: string,
    handleClose: () => void,
    handleAccept: () => void,
    show: boolean
}

const Popup = ({ content, handleClose, handleAccept, show }: Props) => {
    return (
        <Modal show={show} centered>
            <Modal.Body className="popup bg-dark">
                {content}
            </Modal.Body>
            <Modal.Footer className="bg-dark">
                <Button variant="danger" onClick={handleClose}>Close</Button>
                <Button variant="success" onClick={handleAccept}>Confirm</Button>
            </Modal.Footer>
        </Modal>
    );
}

export default Popup;