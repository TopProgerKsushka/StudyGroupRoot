const q = (s) => document.querySelector(s);
const qa = (s) => document.querySelectorAll(s);

const PAGE_MAIN = q('#page-main');
const PAGE_ADD_GROUP = q('#page-add-group');
const PAGE_PUT_GROUP = q('#page-put-group');
const PAGE_DELETE_GROUP = q('#page-delete-group');
const PAGE_GET_GROUP = q('#page-get-group');
const PAGE_GET_ALL_GROUPS = q('#page-get-all-groups');

const PAGE_COUNT_HIGHER_SEMESTER = q('#page-count-higher-semester');
const PAGE_PREFIX = q('#page-prefix');
const PAGE_CHANGE_EDU_FORM = q('#page-change-edu-form');

const PAGE_SHOW = q('#page-show');
const PAGE_LIST = q('#page-list');
const PAGE_COUNT = q('#page-count');

const BTN_ADD_GROUP = q('#btn-add-group');
const BTN_PUT_GROUP = q('#btn-put-group');
const BTN_DELETE_GROUP = q('#btn-delete-group');
const BTN_GET_GROUP = q('#btn-get-group');
const BTN_GET_ALL_GROUPS = q('#btn-get-all-groups');

const BTN_COUNT_HIGHER_SEMESTER = q('#btn-count-higher-semester');
const BTN_PREFIX = q('#btn-prefix');
// const BTN_UNIQUE_FORMS = q('#btn-unique-forms');
const BTN_CHANGE_EDU_FORM = q('#btn-change-edu-form');
// const BTN_COUNT_EXPELLED_STUDENTS = q('#btn-count-expelled-students');

const COMBO_ORDER_BY = q('#combo-order-by');
const LIST_ORDER_BY = q('#list-order-by');
const HIDDEN_ORDER_BY = q('#hidden-order-by');
const BTN_ORDER_BY_PLUS = q('#btn-order-by-plus');
const BTN_ORDER_BY_MINUS = q('#btn-order-by-minus');
const BTN_ORDER_BY_HIGHER = q('#btn-order-by-higher');
const BTN_ORDER_BY_LOWER = q('#btn-order-by-lower');

const COMBO_FILTER_BY = q('#combo-filter-by');
const LIST_FILTER_BY = q('#list-filter-by');
const HIDDEN_FILTER_BY = q('#hidden-filter-by');
const BLOCK_FILTER_BY = q('#block-filter-by');
const BTN_FILTER_BY_PLUS = q('#btn-filter-by-plus');
const BTN_FILTER_BY_MINUS = q('#btn-filter-by-minus');

const BALLOON_LAYER = q('#balloon-layer');

const GROUPLIST = q('#grouplist');
const LIST = q('#list');
const HUGE_NUMBER = q('#huge-number');

const FIELD_SETTINGS = {
  'id': {
    type: 'text',
    numeric: true,
  },
  'name': {
    type: 'text',
  },
  'coordinates.x': {
    type: 'text',
    numeric: true,
  },
  'coordinates.y': {
    type: 'text',
    numeric: true,
    float: true,
  },
  'creationDate': {
    type: 'text',
  },
  'studentsCount': {
    type: 'text',
    numeric: true,
  },
  'formOfEducation': {
    type: 'enum',
    variants: [
      {
        display: 'Дистанционное обучение',
        value: 'DISTANCE_EDUCATION',
      },
      {
        display: 'Полный день',
        value: 'FULL_TIME_EDUCATION',
      },
      {
        display: 'Вечернее обучение',
        value: 'EVENING_CLASSES',
      },
    ],
  },
  'semesterEnum': {
    type: 'enum',
    variants: [
      {
        display: 'Второй',
        value: 'SECOND',
      },
      {
        display: 'Третий',
        value: 'THIRD',
      },
      {
        display: 'Четвёртый',
        value: 'FOURTH',
      },
      {
        display: 'Пятый',
        value: 'FIFTH',
      },
      {
        display: 'Седьмой',
        value: 'SEVENTH',
      },
    ],
  },
  'groupAdmin.name': {
    type: 'text',
    optional: true,
  },
  'groupAdmin.passportID': {
    type: 'text',
    minLength: 7,
    optional: true,
  },
  'groupAdmin.eyeColor': {
    type: 'enum',
    variants: [
      {
        display: 'Зелёный',
        value: 'GREEN',
      },
      {
        display: 'Чёрный',
        value: 'BLACK',
      },
      {
        display: 'Жёлтый',
        value: 'YELLOW',
      },
      {
        display: 'Оранжевый',
        value: 'ORANGE',
      },
      {
        display: 'Белый',
        value: 'WHITE',
      },
    ],
  },
  'groupAdmin.hairColor': {
    type: 'enum',
    variants: [
      {
        display: 'Синий',
        value: 'BLUE',
      },
      {
        display: 'Чёрный',
        value: 'BLACK',
      },
      {
        display: 'Коричневый',
        value: 'BROWN',
      },
      {
        display: 'Оранжевый',
        value: 'ORANGE',
      },
      {
        display: 'Белый',
        value: 'WHITE',
      },
    ],
  },
  'groupAdmin.nationality': {
    type: 'enum',
    variants: [
      {
        display: 'Китаец',
        value: 'CHINA',
      },
      {
        display: 'Индус',
        value: 'INDIA',
      },
      {
        display: 'Японец',
        value: 'JAPAN',
      },
      {
        display: 'Итальянец',
        value: 'ITALY',
      },
    ],
  },
  'pageNumber': {
    type: 'text',
    numeric: true,
    optional: true,
  },
  'pageSize': {
    type: 'text',
    numeric: true,
    optional: true,
  },
};

window.onload = () => {
  BTN_ADD_GROUP.onclick = () => {
    switchPage(PAGE_ADD_GROUP, 'left');
  };

  BTN_PUT_GROUP.onclick = () => {
    switchPage(PAGE_PUT_GROUP, 'left');
  };

  BTN_DELETE_GROUP.onclick = () => {
    switchPage(PAGE_DELETE_GROUP, 'left');
  };

  BTN_GET_GROUP.onclick = () => {
    switchPage(PAGE_GET_GROUP, 'left');
  };

  BTN_GET_ALL_GROUPS.onclick = () => {
    switchPage(PAGE_GET_ALL_GROUPS, 'left');
  };

  BTN_COUNT_HIGHER_SEMESTER.onclick = () => {
    switchPage(PAGE_COUNT_HIGHER_SEMESTER, 'left');
  };

  BTN_PREFIX.onclick = () => {
    switchPage(PAGE_PREFIX, 'left');
  };

  BTN_CHANGE_EDU_FORM.onclick = () => {
    switchPage(PAGE_CHANGE_EDU_FORM, 'left');
  };

  for (const backButton of qa('.back-button')) {
    backButton.onclick = () => {
      switchPage(PAGE_MAIN, 'right');
    };
  }

  // forms
  for (const /** @type HTMLFormElement */ form of qa('form')) {
    const submit = form.querySelector(".submit");
    const spinner = form.querySelector(".spinner");
    submit.onclick = async () => {
      if (spinner) {
        submit.classList.add('hidden');
        spinner.classList.remove('hidden');
      }

      if (validateForm(form)) {
        let data = new FormData(form);
        if (data.entries().next().done) {
          data = undefined;
        }
        const resp = await fetch(form.action, {
          method: form.method,
          body: data,
        });
        const respData = await resp.json();
        if (respData.action === 'goHome') {
          fullReset(form)
          switchPage(PAGE_MAIN, 'right');
        } else if (respData.action === 'balloon') {
          balloon(respData.message);
        } else if (respData.action === 'show') {
          fullReset(form);
          fillGrouplist(respData.groups);
          switchPage(PAGE_SHOW, 'left');
        } else if (respData.action === 'list') {
          console.log(respData.list);
          fullReset(form);
          fillList(respData.list);
          switchPage(PAGE_LIST, 'left');
        } else if (respData.action === 'number') {
          fullReset(form);
          HUGE_NUMBER.innerText = respData.number;
          switchPage(PAGE_COUNT, 'left');
        } else {
          // throw new Error('Unknown response from API');
          balloon('Error: Unknown response from API');
        }
      }

      if (spinner) {
        spinner.classList.add('hidden');
        submit.classList.remove('hidden');
      }
    };
  }

  // combos and lists
  // ORDER_BY
  LIST_ORDER_BY.onchange = () => {
    if (LIST_ORDER_BY.selectedOptions.length === 0 || LIST_ORDER_BY.selectedOptions.length >= LIST_ORDER_BY.options.length) {
      BTN_ORDER_BY_MINUS.disabled = true;
      BTN_ORDER_BY_HIGHER.disabled = true;
      BTN_ORDER_BY_LOWER.disabled = true;
    } else {
      BTN_ORDER_BY_MINUS.disabled = false;
      const first = LIST_ORDER_BY.selectedOptions[0];
      const last = LIST_ORDER_BY.selectedOptions[LIST_ORDER_BY.selectedOptions.length - 1];
      BTN_ORDER_BY_HIGHER.disabled = first.index === 0;
      BTN_ORDER_BY_LOWER.disabled = last.index === LIST_ORDER_BY.options.length - 1;
    }
  };

  BTN_ORDER_BY_PLUS.onclick = () => {
    const selectedIndex = COMBO_ORDER_BY.selectedIndex;
    const selected = COMBO_ORDER_BY.options[selectedIndex];

    LIST_ORDER_BY.append(COMBO_ORDER_BY.querySelector(`option[value='${selected.value}']`).cloneNode(true));
    selected.disabled = true;
    for (const option of Array.from(COMBO_ORDER_BY.options)) {
      if (!option.disabled) {
        option.selected = true;
        break;
      }
    }

    LIST_ORDER_BY.onchange();
    recalculateOrderByHidden();
  };

  BTN_ORDER_BY_MINUS.onclick = () => {
    for (const option of Array.from(LIST_ORDER_BY.selectedOptions).reverse()) {
      LIST_ORDER_BY.options.remove(option.index);
      COMBO_ORDER_BY.querySelector(`option[value='${option.value}']`).disabled = false;
    }

    recalculateOrderByHidden();
  };

  BTN_ORDER_BY_HIGHER.onclick = () => {
    for (let i = 0; i < LIST_ORDER_BY.length; ++i) {
      const option = LIST_ORDER_BY.options[i];
      if (option.selected) {
        const prev = LIST_ORDER_BY.options[i-1];
        const tmpText = prev.text;
        const tmpValue = prev.value;
        const tmpSelected = prev.selected;
        prev.text = option.text;
        prev.value = option.value;
        prev.selected = option.selected;
        option.text = tmpText;
        option.value = tmpValue;
        option.selected = tmpSelected;
      }
    }
    LIST_ORDER_BY.onchange();
    recalculateOrderByHidden();
  };

  BTN_ORDER_BY_LOWER.onclick = () => {
    for (let i = LIST_ORDER_BY.length - 1; i >= 0; --i) {
      const option = LIST_ORDER_BY.options[i];
      if (option.selected) {
        const next = LIST_ORDER_BY.options[i+1];
        const tmpText = next.text;
        const tmpValue = next.value;
        const tmpSelected = next.selected;
        next.text = option.text;
        next.value = option.value;
        next.selected = option.selected;
        option.text = tmpText;
        option.value = tmpValue;
        option.selected = tmpSelected;
      }
    }
    LIST_ORDER_BY.onchange();
    recalculateOrderByHidden();
  };

  // FILTER BY
  COMBO_FILTER_BY.onchange = () => {
    const options = FIELD_SETTINGS[COMBO_FILTER_BY.value];
    switch (options.type) {
      case 'text':
        const input = document.createElement('input');
        input.setAttribute('type', 'text');
        input.setAttribute('id', 'input-filter-by');

        BLOCK_FILTER_BY.innerHTML = '';
        BLOCK_FILTER_BY.appendChild(input);
        break;

      case 'enum':
        const select = document.createElement('select');
        select.setAttribute('id', 'input-filter-by');

        for (const variant of options.variants) {
          const option = document.createElement('option');
          option.setAttribute('value', variant.value);
          option.innerText = variant.display;

          select.appendChild(option);
        }

        BLOCK_FILTER_BY.innerHTML = '';
        BLOCK_FILTER_BY.appendChild(select);
        break;
    }
  };

  LIST_FILTER_BY.onchange = () => {
    BTN_FILTER_BY_MINUS.disabled = LIST_FILTER_BY.selectedOptions.length === 0;
  };

  BTN_FILTER_BY_PLUS.onclick = () => {
    const input = q('#input-filter-by');

    if (!validate(COMBO_FILTER_BY.value, input.value)) return;
    const newConditionValue = `${COMBO_FILTER_BY.value}=${input.value}`;
    const filterDisplay = COMBO_FILTER_BY.selectedOptions[0].text;
    const valueDisplay = input.selectedOptions ? input.selectedOptions[0].text : input.value;
    const newConditionText = `${filterDisplay} = ${valueDisplay}`;

    const newCondition = document.createElement('option');
    newCondition.setAttribute('value', newConditionValue);
    newCondition.innerText = newConditionText;

    LIST_FILTER_BY.append(newCondition);
    recalculateFilterByHidden();
  };

  BTN_FILTER_BY_MINUS.onclick = () => {
    for (const option of Array.from(LIST_FILTER_BY.selectedOptions).reverse()) {
      LIST_FILTER_BY.options.remove(option.index);
    }

    recalculateFilterByHidden();
  };

  // so be it, you can skip the beautiful splash animation for the second time 0:)
  setTimeout(() => {
    fetch("/splash-can-be-skipped", {
      method: 'post',
    });
  }, 9300);
};

function recalculateOrderByHidden() {
  HIDDEN_ORDER_BY.value = Array.from(LIST_ORDER_BY.options).map(o => o.value).join(',');
}

function recalculateFilterByHidden() {
  HIDDEN_FILTER_BY.value = Array.from(LIST_FILTER_BY.options).map(o => o.value).join(',');
}

function switchPage(nextPage, direction) {
  const _hiding = `hiding-${direction}`;
  const _showing = `showing-${direction}`;
  const currentPage = q('.page.current');
  currentPage.classList.remove('current');
  currentPage.classList.add(_hiding);
  setTimeout(() => {
    currentPage.classList.remove(_hiding);
    currentPage.classList.add('hidden');
    nextPage.classList.remove('hidden');
    nextPage.classList.add('current');
    nextPage.classList.add(_showing);
    setTimeout(() => {
      nextPage.classList.remove(_showing);
    }, 300);
  }, 300);
}

function balloon(message) {
  const balloon = document.createElement('div');
  balloon.classList.add('balloon');
  balloon.innerText = message;
  BALLOON_LAYER.append(balloon);
  setTimeout(() => {
    balloon.remove();
  }, 7000);
}

function fillGrouplist(groups) {
  GROUPLIST.innerHTML = '';
  for (const group of groups) {
    const tr = document.createElement('tr');

    const id = document.createElement('td');
    const name = document.createElement('td');
    const x = document.createElement('td');
    const y = document.createElement('td');
    const studentsCount = document.createElement('td');
    const formOfEducation = document.createElement('td');
    const semesterEnum = document.createElement('td');
    const groupAdminName = document.createElement('td');
    const groupAdminPassportID = document.createElement('td');
    const groupAdminEyeColor = document.createElement('td');
    const groupAdminHairColor = document.createElement('td');
    const groupAdminNationality = document.createElement('td');

    id.innerText = group.id;
    name.innerText = group.name;
    x.innerText = group.coordinates.x;
    y.innerText = group.coordinates.y;
    studentsCount.innerText = group.studentsCount;
    formOfEducation.innerText = group.formOfEducation;
    semesterEnum.innerText = group.semesterEnum;
    if (!group.groupAdmin) {
      groupAdminName.innerText = '—';
      groupAdminPassportID.innerText = '—';
      groupAdminEyeColor.innerText = '—';
      groupAdminHairColor.innerText = '—';
      groupAdminNationality.innerText = '—';
    } else {
      groupAdminName.innerText = group.groupAdmin.name;
      groupAdminPassportID.innerText = group.groupAdmin.passportID;
      groupAdminEyeColor.innerText = group.groupAdmin.eyeColor;
      groupAdminHairColor.innerText = group.groupAdmin.hairColor;
      groupAdminNationality.innerText = group.groupAdmin.nationality;
    }

    tr.append(id);
    tr.append(name);
    tr.append(x);
    tr.append(y);
    tr.append(studentsCount);
    tr.append(formOfEducation);
    tr.append(semesterEnum);
    tr.append(groupAdminName);
    tr.append(groupAdminPassportID);
    tr.append(groupAdminEyeColor);
    tr.append(groupAdminHairColor);
    tr.append(groupAdminNationality);

    GROUPLIST.append(tr);
  }
}

function fillList(lines) {
  LIST.innerHTML = '';
  for (const line of lines) {
    const tr = document.createElement('tr');
    const td = document.createElement('td');
    td.innerText = line;
    tr.append(td);
    LIST.append(tr);
  }
}

function validate(field, value) {
  const options = FIELD_SETTINGS[field];
  let variantExists = false;
  switch (options.type) {
    case 'text':
      if (!value || value === '') {
        balloon(`Значение текстового поля '${field}' не должно быть пустым`);
        return false;
      }
      if (options.minLength && value.length < options.minLength) {
        balloon(`Текстовое поле '${field}' должно содержать не менее ${options.minLength} знаков`);
        return false;
      }
      if (options.numeric && !options.float && !/^\d+$/.test(value)) {
        balloon(`Неверное значение целочисленного поля '${field}'`);
        return false;
      }
      if (options.float && isNaN(value)) {
        balloon(`Неверное значение поля с плавающей точкой '${field}'`);
        return false;
      }
      break;

    case 'enum':
      for (const variant of options.variants) {
        if (variant.value === value) {
          variantExists = true;
          break;
        }
      }
      if (!variantExists) {
        balloon(`Указанного варианта перечисления '${field}' не существует`);
        return false;
      }
      break;
  }
  return true;
}

function validateForm(form) {
  let ok = true;
  for (const field of form) {
    if (field.name && FIELD_SETTINGS[field.name]) {
      const value = form[field.name].value;
      if ((value === null || value === '') && FIELD_SETTINGS[field.name].optional) continue;
      if (!validate(field.name, value)) {
        field.classList.add('wrong');
        field.classList.add('shake');
        setTimeout(() => {
          field.classList.remove('shake');
        }, 300);
        ok = false;
      }
    }
  }
  return ok;
}

function fullReset(form) {
  form.reset();
  for (const field of form) {
    field.classList.remove('wrong');
  }

  if (form.name === 'getAll') {
    for (const option of Array.from(COMBO_ORDER_BY.options)) {
      option.disabled = option.value === 'id';
    }
    LIST_ORDER_BY.innerHTML = '';
    const option = document.createElement('option');
    option.setAttribute('value', 'id');
    option.innerText = 'ID';
    LIST_ORDER_BY.append(option);

    LIST_FILTER_BY.innerHTML = '';

    recalculateOrderByHidden();
    recalculateFilterByHidden();
  }
}
