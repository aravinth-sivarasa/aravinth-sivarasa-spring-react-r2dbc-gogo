import React from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import IntlMessages from 'helpers/IntlMessages';
import WorklogAdd from './worklog-add';

const WorklogAddModal = ({ showModal, closeModal }) => {
  return (
    <Modal isOpen={showModal} toggle={() => closeModal(!showModal)}>
      <ModalHeader>
        {' '}
        <IntlMessages id="worklog.add.modal.title" />
      </ModalHeader>
      <ModalBody>
        {' '}
        <WorklogAdd />
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={() => closeModal(false)}>
          Cancel
        </Button>
      </ModalFooter>
    </Modal>
  );
};
export default WorklogAddModal;
