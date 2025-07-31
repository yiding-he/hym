export class FormConfig {
  labelAlign: 'left' | 'center' | 'right' = 'left';
  fieldWidth: string = '200px';
  direction: 'row' | 'column' = 'row';
  fields: FieldConfig[] = [];

  constructor(config?: Partial<FormConfig>) {
    if (config) {
      Object.assign(this, config);
    }
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

export class FieldConfig {
  label: string = "";
  name: string = "";
  type: FieldType = FieldType.Text;
  placeholder: string = "";
  maxLength: number = 50;
  autofocus: boolean = false;
  disabled: boolean = false;

  constructor(config?: Partial<FieldConfig>) {
    if (config) {
      Object.assign(this, config);
    }
  }
}
