export type FormConfig = {
  labelAlign: 'left' | 'center' | 'right';
  fieldWidth: string;
  fields: FieldConfig[];
}

export const NewFormConfig = (obj: any): FormConfig => {
  return {
    labelAlign: 'left',
    fieldWidth: '200px',
    fields: [],
    ...obj,
  }
}

export enum FieldType {
  Text = 'text',
  Password = 'password',
  Number = 'number',
  Select = 'select',
  Checkbox = 'checkbox',
  Radio = 'radio',
  Date = 'date',
  Time = 'time',
  DateTime = 'datetime',
  File = 'file',
  Image = 'image',
  Textarea = 'textarea',
  Color = 'color',
}

export type FieldConfig = {
  label: string;
  name: string;
  type: FieldType;
  placeholder: string;
  maxLength: number;
}

export const NewFieldConfig = (obj: any): FieldConfig => {
  return {
    label: '',
    name: '',
    type: FieldType.Text,
    placeholder: '',
    maxLength: 0,
    ...obj,
  }
}
