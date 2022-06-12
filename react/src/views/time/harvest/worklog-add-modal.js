import React, { useState } from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';

const WorklogAddModal = ({ showModal, closeModal }) => {
  const [modalNested, setModalNested] = useState(false);
  const [closeAll, setCloseAll] = useState(false);

  return (
    <Modal isOpen={showModal} toggle={() => closeModal(!showModal)}>
      <ModalHeader>Modal title</ModalHeader>
      <ModalBody>
        {' '}
        Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
        tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim
        veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
        commodo consequat. Duis aute irure dolor in reprehenderit in voluptate
        velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint
        occaecat cupidatat non proident, sunt in culpa qui officia deserunt
        mollit anim id est laborum.
        <br />
        <div className="text-center m-2">
          <Button
            color="primary"
            outline
            onClick={() => {
              setCloseAll(false);
              setModalNested(true);
            }}
          >
            Show Nested Modal
          </Button>
        </div>
        <Modal
          isOpen={modalNested}
          toggle={() => setModalNested(!modalNested)}
          onClosed={closeAll ? () => closeModal(false) : () => {}}
        >
          <ModalHeader>Nested Modal title</ModalHeader>
          <ModalBody>Stuff and things</ModalBody>
          <ModalFooter>
            <Button color="primary" onClick={() => setModalNested(false)}>
              Done
            </Button>{' '}
            <Button
              color="secondary"
              onClick={() => {
                setCloseAll(true);
                setModalNested(false);
              }}
            >
              All Done
            </Button>
          </ModalFooter>
        </Modal>
      </ModalBody>
      <ModalFooter>
        <Button color="primary" onClick={() => closeModal(false)}>
          Do Something
        </Button>{' '}
        <Button color="secondary" onClick={() => closeModal(false)}>
          Cancel
        </Button>
      </ModalFooter>
    </Modal>
  );
};
export default WorklogAddModal;
