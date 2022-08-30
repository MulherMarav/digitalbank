package br.com.digitalbank.conta.models.conta;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.digitalbank.conta.mapper.DozerMapper;
import br.com.digitalbank.conta.models.movimentacao.Movimentacao;
import br.com.digitalbank.conta.models.movimentacao.Rendimento;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "poupanca")
@Data @NoArgsConstructor
public class Poupanca extends Conta implements Serializable {

	private static final long serialVersionUID = 1L;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "poupanca_id")
	private List<Rendimento> rendimentos;
	
	private LocalDateTime ultimoRendimento;


	@Override
	public void movimentaNovoValor(BigDecimal percentual) {
		BigDecimal novoValor = super.valor.multiply(percentual);
		super.valor = valor.add(novoValor);
		this.ultimoRendimento = LocalDateTime.now();
	}

	@Override
	public void adicionaMovimentacao(Movimentacao movimentacao) {

		Rendimento rendimento = DozerMapper.parseObject(movimentacao, Rendimento.class);
		
		if (rendimentos == null)
			rendimentos = new ArrayList<Rendimento>();

		rendimentos.add(rendimento);
	}
	
	@Override
	public LocalDateTime dataUltimaMovimentacao() {
		return this.ultimoRendimento;
	}
}
