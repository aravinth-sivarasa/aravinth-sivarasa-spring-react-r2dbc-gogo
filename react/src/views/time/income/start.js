import React, { useState } from 'react';

import IconCard from 'components/cards/IconCard';
import { Colxx, Separator } from 'components/common/CustomBootstrap';
import Breadcrumb from 'containers/navs/Breadcrumb';
import { Row, Button } from 'reactstrap'; //
import IntlMessages from 'helpers/IntlMessages';
import { ReactTableDivided } from './react-table-divided';
import WorklogAddModal from './worklog-add-modal';

import 'react-datepicker/dist/react-datepicker.css';

const data = [
  {
    title: 'income.total',
    value: 74,
  },
];

export const Start = ({ match }) => {
  const [showWorklogAddModal, setShowWorklogAddModal] = useState(false);
  return (
    <>
      <Row>
        <Colxx xxs="12">
          <Breadcrumb heading="menu.income" match={match} />
          <Separator className="mb-5" />
        </Colxx>
      </Row>
      <Row>
        <Colxx xl="6" lg="12" className="mb-4">
          <Row className="icon-cards-row mb-2">
            {data.map((item) => {
              return (
                <Colxx xl="3" lg="2" key={`icon_card_${item.title}`}>
                  <IconCard {...item} className="mb-4" />
                </Colxx>
              );
            })}
          </Row>
          <Row>
            <Button
              color="primary"
              outline
              onClick={() => setShowWorklogAddModal(true)}
            >
              <IntlMessages id="income.btn.popup.add" />
            </Button>
            <WorklogAddModal
              showModal={showWorklogAddModal}
              closeModal={setShowWorklogAddModal}
            />
          </Row>
        </Colxx>
        <Colxx xl="6" lg="12" className="mb-4">
          <ReactTableDivided />
        </Colxx>
      </Row>
    </>
  );
};

export default Start;
