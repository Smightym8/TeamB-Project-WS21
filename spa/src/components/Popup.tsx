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
        <Modal show={show} centered >

            <Modal.Body>
                {content}
            </Modal.Body>
            <Modal.Footer>
                <Button onClick={handleClose}>Close</Button>
                <Button onClick={handleAccept}>Confirm</Button>
            </Modal.Footer>
        </Modal>
    );
}

export default Popup;