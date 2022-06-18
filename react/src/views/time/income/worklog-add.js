/* eslint-disable jsx-a11y/label-has-for */
/* eslint-disable jsx-a11y/label-has-associated-control */
import DatePicker from 'react-datepicker';
import React, { useState } from 'react';
import IntlMessages from 'helpers/IntlMessages';
import { Row, Button, FormGroup, Label, Form } from 'reactstrap'; //
import { Colxx } from 'components/common/CustomBootstrap';
import 'react-tagsinput/react-tagsinput.css';
import 'react-datepicker/dist/react-datepicker.css';
import 'rc-switch/assets/index.css';
import 'rc-slider/assets/index.css';
import 'react-rater/lib/react-rater.css';

import Select from 'react-select';
import CustomSelectInput from 'components/common/CustomSelectInput';
import { NotificationManager } from 'components/common/react-notifications';

const selectData = [
  { label: 'Cake', value: 'cake', key: 0 },
  { label: 'Cupcake', value: 'cupcake', key: 1 },
  { label: 'Dessert', value: 'dessert', key: 2 },
];

const WorklogAdd = () => {
  const [startDateTime, setStartDateTime] = useState(new Date());
  const [endDateTime, setEndDateTime] = useState(new Date());

  const [selectedWorkplace, setSelectedWorkplace] = useState('');
  const createNotification = (type, className) => {
    const cName = className || '';
    switch (type) {
      case 'primary':
        NotificationManager.primary(
          'This is a notification!',
          'Primary Notification',
          3000,
          null,
          null,
          cName
        );
        break;
      case 'secondary':
        NotificationManager.secondary(
          'This is a notification!',
          'Secondary Notification',
          3000,
          null,
          null,
          cName
        );
        break;
      case 'info':
        NotificationManager.info('Info message', '', 3000, null, null, cName);
        break;
      case 'success':
        NotificationManager.success(
          'Success message',
          'Title here',
          3000,
          null,
          null,
          cName
        );
        break;
      case 'warning':
        NotificationManager.warning(
          'Warning message',
          'Close after 3000ms',
          3000,
          null,
          null,
          cName
        );
        break;
      case 'error':
        NotificationManager.error(
          'Error message',
          'Click me!',
          5000,
          () => {
            alert('callback');
          },
          null,
          cName
        );
        break;
      default:
        NotificationManager.info('Info message');
        break;
    }
  };
  return (
    <>
      <Row className="mb-5">
        <Colxx xxs="12">
          <label>
            <IntlMessages id="worklog.add.model.detail" />
          </label>

          <Form>
            <FormGroup className="form-group has-top-label">
              <Label>
                <IntlMessages id="worklog.add.model.date.start" />
              </Label>

              <DatePicker
                selected={startDateTime}
                onChange={setStartDateTime}
                placeholderText={<IntlMessages id="forms.date" />}
                showTimeSelect
                timeFormat="HH:mm"
                timeIntervals={15}
                timeCaption="Time"
                dateFormat="MMMM d, yyyy h:mm aa"
              />
            </FormGroup>
            <FormGroup className="form-group has-top-label">
              <Label>
                <IntlMessages id="worklog.add.model.date.end" />
              </Label>

              <DatePicker
                selected={endDateTime}
                onChange={setEndDateTime}
                placeholderText={<IntlMessages id="forms.date" />}
                showTimeSelect
                timeFormat="HH:mm"
                timeIntervals={15}
                timeCaption="Time"
                dateFormat="MMMM d, yyyy h:mm aa"
              />
            </FormGroup>

            <FormGroup className="form-group has-top-label">
              <Label>
                <IntlMessages id="worklog.add.model.workplace" />
              </Label>
              <Select
                components={{ Input: CustomSelectInput }}
                className="react-select"
                classNamePrefix="react-select"
                name="form-field-name"
                value={selectedWorkplace}
                onChange={(val) => setSelectedWorkplace(val)}
                options={selectData}
                placeholder=""
              />
            </FormGroup>

            <Button
              color="primary"
              onClick={() => createNotification('success', 'filled')}
            >
              <IntlMessages id="worklog.add.model.btn.add" />
            </Button>
          </Form>
        </Colxx>
      </Row>
    </>
  );
};
export default WorklogAdd;
