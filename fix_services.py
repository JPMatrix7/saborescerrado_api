import os
import re

# Lista de services simples (sem relacionamentos complexos) que precisam ser corrigidos
services_to_fix = [
    'Telefone',
    'Sabor',
    'Premiacao',
    'Ingrediente',
    'Embalagem',
    'Categoria',
    'Avaliacao'
]

base_path = r'c:\Users\JP\Documents\TÓPICOS II\API-Quarkus-TP2\saborescerrado_api\src\main\java\saborescerrado\jp\tp2\service\impl'

for service_name in services_to_fix:
    file_path = os.path.join(base_path, f'{service_name}ServiceImpl.java')
    
    if not os.path.exists(file_path):
        print(f"❌ Arquivo não encontrado: {file_path}")
        continue
    
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # Adicionar import do ResponseDTO se não existir
    if f'{service_name}ResponseDTO' not in content:
        # Encontrar onde adicionar o import
        import_pattern = rf'(import saborescerrado\.jp\.tp2\.dto\.{service_name}DTO;)'
        replacement = rf'\1\nimport saborescerrado.jp.tp2.dto.{service_name}ResponseDTO;'
        content = re.sub(import_pattern, replacement, content)
    
    # Adicionar import List se não existir
    if 'import java.util.List;' not in content:
        content = content.replace('import java.util.Comparator;', 'import java.util.Comparator;\nimport java.util.List;')
    
    # Corrigir método getAll()
    content = re.sub(
        rf'return Response\.ok\(repository\.findAll\(\)\.stream\(\)\s*\.filter\(EntityClass::getAtivo\)\s*\.sorted\(Comparator\.comparing\(EntityClass::getId\)\.reversed\(\)\)\s*\.collect\(Collectors\.toList\(\)\)\)\.build\(\);',
        f'''List<{service_name}ResponseDTO> lista = repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(lista).build();''',
        content
    )
    
    # Corrigir método getAllAdmin()
    content = re.sub(
        rf'return Response\.ok\(repository\.findAll\(\)\.page\(page, pageSize\)\.stream\(\)\s*\.sorted\(Comparator\.comparing\(EntityClass::getId\)\.reversed\(\)\)\s*\.collect\(Collectors\.toList\(\)\)\)\.build\(\);',
        f'''List<{service_name}ResponseDTO> lista = repository.findAll().page(page, pageSize).stream()
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(lista).build();''',
        content
    )
    
    # Corrigir retorno de entidades diretas para toResponseDTO
    lowercase_name = service_name[0].lower() + service_name[1:]
    content = re.sub(
        rf'return Response\.ok\({lowercase_name}\)\.build\(\);',
        f'return Response.ok(toResponseDTO({lowercase_name})).build();',
        content
    )
    
    content = re.sub(
        rf'return Response\.status\(Status\.CREATED\)\.entity\({lowercase_name}\)\.build\(\);',
        f'return Response.status(Status.CREATED).entity(toResponseDTO({lowercase_name})).build();',
        content
    )
    
    # Adicionar método toResponseDTO se não existir
    if 'private ' + service_name + 'ResponseDTO toResponseDTO' not in content:
        # Encontrar o final da classe
        last_brace_pos = content.rfind('}')
        
        # Criar método toResponseDTO
        to_response_method = f'''
    private {service_name}ResponseDTO toResponseDTO({service_name} {lowercase_name}) {{
        return new {service_name}ResponseDTO(
            {lowercase_name}.getId(),
            {lowercase_name}.getNome()
        );
    }}
}}'''
        
        content = content[:last_brace_pos] + to_response_method
    
    # Salvar arquivo
    with open(file_path, 'w', encoding='utf-8') as f:
        f.write(content)
    
    print(f"✅ Corrigido: {service_name}ServiceImpl.java")

print("\n✅ Todos os services foram corrigidos!")
