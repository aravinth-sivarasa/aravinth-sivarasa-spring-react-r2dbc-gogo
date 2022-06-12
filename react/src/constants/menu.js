import { adminUI } from './defaultValues';

const data = [
  {
    id: 'time',
    icon: 'iconsminds-stopwatch',
    label: 'menu.time',
    to: `${adminUI}/time/blank-page`,
    subs: [
      {
        icon: 'iconsminds-forest-1',
        label: 'menu.harvest',
        to: `/ui/time/harvest`,
      },
    ],
  },
];
export default data;
